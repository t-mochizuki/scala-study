class Car(person: Person) {
  def drive(signal: Signal): DrivingState =
    signal match {
      case Red =>
        Stop
      case Yellow =>
        if (person.personality == Impatient) Run
        else Stop
      case Blue =>
        Run
    }
}
