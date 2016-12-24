import org.scalacheck._
import Prop.forAll

object CarSpec extends Properties("Car") {

  def genSignal = Gen.oneOf(Red, Yellow, Blue)

  property("constraint") = forAll(genSignal) { signal =>
    val person = new Person(Gentle)
    val car = new Car(person)
    val drivingState = car.drive(signal)

    val result = signal match {
      case Red =>
        drivingState == Stop
      case Yellow =>
        if (person.personality == Impatient) drivingState == Run
        else drivingState == Stop
      case Blue =>
        drivingState == Run
    }

    result
  }

}
