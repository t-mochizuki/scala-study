package example

object QueryParserAndRenderer extends App {

  val query =
    """
      query FetchLukeAndLeiaAliased(
            $someVar: Int = 1.23
            $anotherVar: Int = 123) @include(if: true) {
        luke: human(id: "1000")@include(if: true){
          friends(sort: NAME)
        }

        leia: human(id: "10103\n \u00F6 ö") {
          name
        }

        ... on User {
          birth{day}
        }

        ...Foo
      }

      fragment Foo on User @foo(bar: 1) {
        baz
      }
    """

  def parseAndRenderer(query: String): Unit = {
    import sangria.ast.Document
    import sangria.parser.QueryParser
    import sangria.renderer.QueryRenderer

    import scala.util.{Failure, Success}

    // Parse GraphQL query
    QueryParser.parse(query) match {
      case Success(document) =>
        // Pretty rendering of the GraphQL query as a `String`
        println(document.renderPretty)

      case Failure(error) =>
        println(s"Syntax error: ${error.getMessage}")
    }
  }

  parseAndRenderer(query)

  def isSyntacticallyCorrect(): Unit = {
    import sangria.ast.Document
    import sangria.macros._

    val queryAst: Document =
      graphql"""
      {
        name
        friends {
          id
          name
        }
      }
      """
  }

  def independentlyFromQueryDocument(): Unit = {
    import sangria.renderer.QueryRenderer
    import sangria.macros._
    import sangria.ast

    val parsed: ast.Value =
      graphqlInput"""
      {
        id: "1234345"
        version: 2 # changed 2 times
        deliveries: [
        {id: 123, received: false, note: null, state: OPEN}
        ]
      }
      """

      println(parsed.renderPretty)
  }

  independentlyFromQueryDocument()

}
