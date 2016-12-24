import org.scalacheck._
import Prop.forAll

object CarSpec extends Properties("Car") {

  def genPersonality = Gen.oneOf(Impatient, Gentle)
  def genPerson = for(personality <- genPersonality) yield new Person(personality)
  def genCar = for(person <- genPerson) yield new Car(person)
  def genSignal = Gen.oneOf(Red, Yellow, Blue)

  property("constraint") = forAll(genCar, genSignal) { (car, signal) =>
    val drivingState = car.drive(signal)

    val result = signal match {
      case Red =>
        drivingState == Stop
      case Yellow =>
        if (car.person.personality == Impatient) drivingState == Run
        else drivingState == Stop
      case Blue =>
        drivingState == Run
    }

    result
  }

}
