import com.google.ads.googleads.lib.GoogleAdsClient
import com.google.ads.googleads.v1.services.SearchGoogleAdsRequest
import java.io.File

// sbt "run 1231231234"
object Main extends App {
  val loginCustomerId = args(0)
  val propertiesFile = new File("src/main/resources/ads.properties")
  val googleAdsClient = GoogleAdsClient.newBuilder().fromPropertiesFile(propertiesFile).build()
  val campaigns = runExample(googleAdsClient, loginCustomerId)

  private[this] def runExample(googleAdsClient: GoogleAdsClient, loginCustomerId: String) {
    val googleAdsServiceClient = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()

    val PAGE_SIZE = 1000
    val request =
      SearchGoogleAdsRequest.newBuilder()
        .setCustomerId(loginCustomerId)
        .setPageSize(PAGE_SIZE)
        .setQuery("SELECT campaign.id, campaign.name FROM campaign ORDER BY campaign.id")
        .build()

    val response = googleAdsServiceClient.search(request)

    val googleAdsRow = response.iterateAll()
    googleAdsRow.forEach { it =>
      println(s"Campaign with ID ${it.getCampaign.getId.getValue} and name '${it.getCampaign.getName.getValue}' was found.")
    }
  }
}

