import com.facebook.ads.sdk.APIContext
import com.facebook.ads.sdk.APIException
import com.facebook.ads.sdk.serverside.*
import com.google.cloud.ReadChannel
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import java.nio.channels.Channels
import java.io.InputStream

object MetaCAPI {
    const val ACCESS_TOKEN: String = "EAAHJSsbxHwwBOZCH7TnKZAZCkGNqNJADJfkyZBQbS6zlDBXFIRVDlBZAtQMHmhqoLyibawmRZCOqiKYGYCom6hdKtlLmZCVGrEmZADWVEY6BMCy6kh94hMuVwwToWtZBRZCO4aR4wMNji9ZAWc9NDB3jZA9bNmVYPdSgXj8zrInIFj3ux5sYv5s8Ew9nXtqZAFME86MsGT2VAjwIYgiMbwqdYEEnMQU3qLSxMZCQUZD"
    //"EAAHJSsbxHwwBO1mZCbcgyxnQkI4nccTfMrnSFpWdDPVW8jQN9IvaGuyrdQllDiLurRSpjQXVuU8z1z4wuNBQSJma8UlVG6bbv6ds0lebaCCpjkBTZC5GSd0V1aIVYFdPlZBhNJQ7PJqz9h22ZBJbl1hRRULHkNxBQfHSZC1lXwZBKsXOxA8MqH4luACZC1jltxg"
    const val PIXEL_ID: String = "1375347419837602"

    @JvmStatic
    fun main(args: Array<String>) {
        val context: APIContext = APIContext(ACCESS_TOKEN).enableDebug(true)
        context.setLogger(System.out)
        val storage: Storage = StorageOptions.getDefaultInstance().service
        val bucketName = "swetha-dev-anaml-io"
        val fileName = "GCP_METACAPI_DP027_OFFLINE_ORDERS_DUMMY20241003090514.csv"

        val blob = storage.get(bucketName, fileName)
        if (blob == null) {
            throw Exception("Blob not found gs://${bucketName}/${fileName}")
        }
        val readChannel: ReadChannel = blob.reader()
        val inputStream: InputStream = Channels.newInputStream(readChannel)
        println(inputStream.read())
//        blob.reader().use { reader ->
//            println(reader.toString())
//        }

        val userData: UserData = UserData()
            .emails(mutableListOf<String>("joe@eg.com"))
            .phones(
                mutableListOf<String>(
                    "12345678901",
                    "14251234567"
                )
            )
            .fbc("fb.1.1554763741205.AbCdEfGhIjKlMnOpQrStUvWxYz1234567890")
            .fbp("fb.1.1558571054389.1098115397")

        val content: Content = Content()
            .productId("product123")
            .quantity(1L)
            .deliveryCategory(DeliveryCategory.home_delivery)

        val customData: CustomData = CustomData()
            .addContent(content)
            .currency("usd")
            .value(123.45f)

        val purchaseEvent: Event = Event()
        purchaseEvent.eventName("Purchase")
            .eventTime(System.currentTimeMillis() / 1000L)
            .userData(userData)
            .customData(customData)
            .eventSourceUrl("http://test")
            .actionSource(ActionSource.website)

        val eventRequest: EventRequest = EventRequest(PIXEL_ID, context)
        eventRequest.addDataItem(purchaseEvent)

        try {
            val response: EventResponse = eventRequest.execute()
            println(java.lang.String.format("Standard API response : %s ", response))
        } catch (e: APIException) {
            e.printStackTrace()
        }
    }
}