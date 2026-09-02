import kotlinx.coroutines.await
import kotlin.js.JsString
import kotlin.js.Promise

@JsFun("""
    () => {
        // Проверяем физическое наличие метода. 
        // Дополнительно страхуемся: если мы в обычном браузере на localhost/обычном домене, 
        // и у нас нет специфичных для MAX объектов в window, сразу возвращаем false.
        if (typeof window === "undefined" || !window.WebApp || typeof window.WebApp.openCodeReader !== "function") {
            return false;
        }
        
        // Жесткая проверка: в обычном вебе SDK возвращает "web". 
        // Если поле не успело проинициализироваться (undefined), также считаем мост недоступным.
        const platform = window.WebApp.platform;
        if (!platform || platform === "web") {
            return false;
        }
        
        return true;
    }
""")
private external fun isMaxBridgeAvailable(): Boolean

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("""
    (fileSelect) => {
        // Создаем чистый JS Promise. Никакой код снаружи не сможет вызвать синхрейт-падение рантайма.
        return new Promise((resolve, reject) => {
            try {
                const webApp = window.WebApp;
                if (!webApp || typeof webApp.openCodeReader !== "function") {
                    reject(new Error("MAX Bridge is not fully initialized or unavailable"));
                    return;
                }
                
                // Вызываем нативный метод внутри безопасного контекста
                webApp.openCodeReader(fileSelect)
                    .then((res) => { 
                        resolve(JSON.stringify(res))    
                    })
                    .catch((err) => reject(err));
                    
            } catch (error) {
                // Ловим пресловутый `sendFallback` и `transport недоступен` прямо здесь
                reject(error);
            }
        });
    }
""")
private external fun openMaxCodeReader(fileSelect: Boolean): Promise<JsString>

private class MaxQrCodeScanner : QrCodeScanner {

    override suspend fun scan(
        fileSelect: Boolean,
    ): QrCodeScannerResult {

        // Теперь эта проверка железно отработает в Webpack Dev Server
        if (!isMaxBridgeAvailable()) {
            return QrCodeScannerResult.Unavailable
        }

        return try {
            val result = openMaxCodeReader(fileSelect)
                .await<JsString>()
                .toString()

            QrCodeScannerResult.Success(value = result)
        } catch (throwable: Throwable) {
            QrCodeScannerResult.Error(cause = throwable)
        }
    }
}

actual fun createQrCodeScanner(): QrCodeScanner {
    return MaxQrCodeScanner()
}
