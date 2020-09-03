package api_client

object Main extends App {

  args match {
    case Array() => println("An argument is required")
    case Array(accountId, accessToken) =>
      new CampaignServiceApi(accessToken)
        .campaignServiceGetPost(
          CampaignServiceSelector(
            accountId = accountId.toLong,
            containsLabelIdFlg = true,
            startIndex = 1,
            numberResults = 1
          )
        )
  }

}
