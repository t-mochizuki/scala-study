import org.scalacheck._
import Prop.forAll

object CarSpec extends Properties("Car") {

  def genOptionSignal = Gen.oneOf((Some(Signal), None, None), (None, Some(Signal), None), (None, None, Some(Signal)))

  property("constraint") = forAll(genOptionSignal) { signal =>
    val (red, yellow, blue) = signal
    val person = new Person(Gentle)
    val car = new Car(person)
    val drivingState = car.drive(red, yellow, blue)

    val result = signal match {
      case (Some(r), None, None) =>
        drivingState == Stop
      case (None, Some(y), None) =>
        if (person.personality == Impatient) drivingState == Run
        else drivingState == Stop
      case (None, None, Some(b)) =>
        drivingState == Run
    }

    result
  }

}
