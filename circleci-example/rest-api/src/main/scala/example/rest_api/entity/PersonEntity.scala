package example.rest_api.entity

import java.time.ZonedDateTime

final case class PersonEntity(id: Int, name: String, createdAt: ZonedDateTime)
