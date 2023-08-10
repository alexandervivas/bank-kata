package example.domain

import java.time.{ZoneId, ZonedDateTime}

case class ZonedDateTimeWrapper() {

  def now(zoneId: ZoneId): ZonedDateTime = ZonedDateTime.now(zoneId)

}
