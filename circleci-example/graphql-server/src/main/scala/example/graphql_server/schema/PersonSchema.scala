package example.graphql_server.schema

import example.rest_api.entity.PersonEntity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import sangria.ast.StringValue
import sangria.marshalling.DateSupport
import sangria.schema._
import sangria.validation.ValueCoercionViolation
import scala.util.{Failure, Success, Try}

trait PersonSchema {

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

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val PictureType = ObjectType(
    "Person",
    "The person",
    fields[Unit, PersonEntity](
      Field("id", IntType, resolve = _.value.id),
      Field("name", StringType, resolve = _.value.name),
      Field("createdAt", ZonedDateTimeType, resolve = _.value.createdAt)
    )
  )

}
