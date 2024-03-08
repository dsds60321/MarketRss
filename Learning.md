# 프로젝트 개발 통해 얻은 내용

## DDD 아키텍쳐 
1. application: 응용 계층이 위치하는 곳입니다. 여기에는 비즈니스 로직이 구현된 서비스 클래스가 위치합니다.
2. domain: 도메인 계층이 위치하는 곳입니다. 여기에는 도메인 모델(엔티티, 값 객체, 도메인 서비스 등)이 위치합니다.
3. infrastructure: 인프라스트럭처 계층이 위치하는 곳입니다. 여기에는 데이터베이스, 파일 시스템, 메일 서버와 같은 외부 시스템과의 연결 코드가 위치합니다.
4. interfaces: 인터페이스 계층이 위치하는 곳입니다. 여기에는 사용자 인터페이스(UI) 또는 외부 시스템과 통신하는 API 등이 위치합니다.


## Java
### 정적 팩토리 메소드
* payload를 받는 부분에 정적 팩토리 메소드를 도입하려고 한다. 정적 팩토리 메소드를 씀으로써에 대한 이점 정리
1. 명확한 네이밍 : 생성자에 비해 정적 팩토리 메소드는 인스턴스에 의도를 전달할 수 있습니다.
2. 캐싱을 통한 리소스 절약 : 매번 객체를 생성하지 않으므로 불변객체에 경우 객체 재사용을 통해 메모리를 절약할 수 있습니다.
3. 서브 타입 반환 : 매번 같은 객체에 타입이 아니라 서브타입의 객체를 반환 할 수 있습니다. 구현의 유현성을 갖출 수 있습니다.
4. 입력 매개변수에 따라 다른 클래스의 객체를 반환하여 유연성을 높일 수 있습니다. 
* 예제 코드
```java
public class Car {

    private final String make;
    private final String model;
    private final int year;

    // private constructor
    private Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    // Static factory method with explicit name
    public static Car newSportsCar(String model, int year) {
        return new Car("Sports", model, year);
    }

    // another static factory method
    public static Car newFamilyCar(String model, int year) {
        return new Car("Family", model, year);
    }

    // Other methods...
}

public class CarFactoryExample {
    public static void main(String[] args) {
        Car sportsCar = Car.newSportsCar("Ferrari", 2022);
        Car familyCar = Car.newFamilyCar("Minivan", 2023);

        // do something with the cars...
    }
}
```


출처 : https://tecoble.techcourse.co.kr/post/2020-05-26-static-factory-method/
--- 
## JPA
* 
---
## WebClient
