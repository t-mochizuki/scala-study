class Car(person: Person) {
  def drive(red: Option[Signal], yellow: Option[Signal], blue: Option[Signal]): DrivingState =
    (red, yellow, blue) match {
      case (Some(r), None, None) =>
        Stop
      case (None, Some(y), None) =>
        if (person.personality == Impatient) Run
        else Stop
      case (None, None, Some(b)) =>
        Run
    }
}
