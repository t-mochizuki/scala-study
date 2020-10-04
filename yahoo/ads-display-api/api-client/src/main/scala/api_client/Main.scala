package api_client

object Main extends App {

  args match {
    case Array() => println("An argument is required")
    case Array(accountId, accessToken) =>
      new MediaServiceApi(accessToken)
        .mediaServiceAddPost(
          MediaServiceOperation(
            accountId = accountId.toLong,
            operand = Nil))
  }

}
