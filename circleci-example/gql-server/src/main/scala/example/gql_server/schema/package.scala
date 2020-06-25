package example.gql_server

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import sangria.ast.StringValue
import sangria.marshalling.DateSupport
import sangria.schema._
import sangria.validation.ValueCoercionViolation
import scala.util.{Failure, Success, Try}

package object schema {

  case object ZonedDateTimeCoercionViolation
      extends ValueCoercionViolation("ZonedDateTime value expected")

  def parseZonedDateTime(s: String) =
    Try(ZonedDateTime.parse(s)) match {
      case Success(date) => Right(date)
      case Failure(_) => Left(ZonedDateTimeCoercionViolation)
    }

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  implicit val ZonedDateTimeType = ScalarType[ZonedDateTime](
    "ZonedDateTime",
    coerceOutput = (d, caps) =>
      if (caps.contains(DateSupport)) Date.from(d.toInstant)
      else DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX'['VV']'").format(d),
    coerceUserInput = {
      case s: String => parseZonedDateTime(s)
      case _ => Left(ZonedDateTimeCoercionViolation)
    },
    coerceInput = {
      case StringValue(s, _, _, _, _) => parseZonedDateTime(s)
      case _ => Left(ZonedDateTimeCoercionViolation)
    }
  )

}
