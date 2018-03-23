package example

import java.time.{Duration, ZonedDateTime}

import org.scalatest.{FunSpec, MustMatchers}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar

class EmployeeSpec extends FunSpec with MustMatchers with MockitoSugar {

  describe("Employee") {
    it("work") {
      val employee = new Employee
      employee.work(Duration.ZERO) mustBe 0

      val now = ZonedDateTime.now
      val between = Duration.between(now, now.plusDays(3))
      employee.work(between) mustBe 72
    }

    it("mock & verify") {
      val mockEmployee = mock[Employee]

      val now = ZonedDateTime.now
      val between = Duration.between(now, now.plusDays(3))

      when(mockEmployee.work(between)).thenReturn(0)

      mockEmployee.work(between) mustBe 0
      verify(mockEmployee).work(between)
      verify(mockEmployee, never).work(Duration.ZERO)
    }

    it("spy & verify") {
      val employee = new Employee
      val spyEmployee = spy(employee)

      val now = ZonedDateTime.now
      val between = Duration.between(now, now.plusDays(3))

      when(spyEmployee.work(between)).thenReturn(57)

      spyEmployee.work(between) mustBe 57
      verify(spyEmployee).work(between)
      verify(spyEmployee, never).work(Duration.ZERO)
    }
  }
}
