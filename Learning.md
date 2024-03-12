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

### MapStruct 와 빌더 패턴 차이
MapStruct를 사용해 Message 클래스를 구현하는 방식은 좋은 선택일 수 있습니다, 특히 객체 간의 변환 작업이 필요할 때 매우 유용합니다. MapStruct는 구조화된 데이터 매핑을 위한 Java 어노테이션 프로세서입니다. 주로 한 객체의 필드를 다른 객체의 필드로 매핑할 때 사용되며, 수동으로 getter와 setter를 호출하여 필드를 복사하는 대신, 이를 자동화하여 코드의 양을 줄이고 유지 보수성을 향상시킵니다.

MapStruct와 빌더 패턴은 서로 다른 문제를 해결하기 위해 사용됩니다. 빌더 패턴은 객체 생성 과정을 더 안전하고, 이해하기 쉬우며, 유연하게 만들어주는 반면, MapStruct는 주로 다른 타입의 객체 간의 데이터 매핑을 자동화하여 개발자가 수작업으로 변환 로직을 작성하는 것을 줄여줍니다. 따라서, Message 클래스의 인스턴스를 생성하는 것이 주 목적이라면 빌더 패턴이 더 적합할 수 있고, Message 객체를 다른 타입의 객체로 변환하는 작업이 자주 필요하다면 MapStruct를 고려하는 것이 좋습니다.

예를 들어, MessageDTO 클래스의 인스턴스를 Message 엔티티 객체로 변환하는 경우가 자주 있다면, MapStruct를 사용하여 변환 로직을 자동화할 수 있습니다. MapStruct를 사용하면 변환 로직을 별도의 클래스로 분리하여 관리할 수 있으며, 코드 생성을 통해 성능에도 이점을 가져올 수 있습니다.

MapStruct를 사용한 Message 클래스 예시는 다음과 같습니다:

~~~java
@Mapper
public interface MessageMapper {
MessageMapper INSTANCE = Mappers.getMapper( MessageMapper.class );

    @Mapping(source = "text", target = "content")
    Message messageDtoToMessage(MessageDTO messageDTO);
}
~~~
위 예제에서 MessageMapper 인터페이스는 MapStruct에 의해 구현됩니다. @Mapping 어노테이션을 사용하여 MessageDTO의 text 필드가 Message의 content 필드로 매핑되도록 지정할 수 있습니다. 이렇게 하면 복잡한 변환 로직을 간단한 어노테이션 설정으로 해결할 수 있으며, 코드의 가독성과 유지 보수성이 크게 향상됩니다.

따라서, 당신의 요구 사항과 프로젝트의 특성에 따라 MapStruct의 사용 여부를 결정하면 좋을 것 같습니다.


---

### RedisTemplate
* opsFor~ 로 시작하는 메소드는 Redis에서 지원하는 특정 타입의 데이터 구조에 대한 작업을 수행하는 연산자를 반환합니다
1. opsForValue(): 이 메소드는 Redis의 String(또는 Value) 타입의 데이터에 대한 작업을 수행합니다. 예를 들면, 값을 설정하거나 가져오는 등의 작업을 할 수 있습니다.
2. opsForList(): 이 메소드는 Redis의 List 타입의 데이터에 대한 작업을 수행합니다. 예를 들면, 리스트의 맨 앞이나 끝에 요소를 추가하거나, 특정 범위의 요소를 조회하는 등의 작업을 할 수 있습니다.
3. opsForSet(): 이 메소드는 Redis의 Set 타입의 데이터에 대한 작업을 수행합니다. 예를 들면, 집합에 요소를 추가하거나 삭제하고, 두 집합의 교집합이나 합집합 등을 구하는 작업을 할 수 있습니다.
4. opsForZSet(): 이 메소드는 Redis의 Sorted Set(ZSet) 타입의 데이터에 대한 작업을 수행합니다. 예를 들면, 점수를 기준으로 요소를 추가하거나 삭제하고, 점수 범위나 순위에 따른 요소를 조회하는 등의 작업을 할 수 있습니다.
5. opsForHash(): 이 메소드는 Redis의 Hash 타입의 데이터에 대한 작업을 수행합니다. 예를 들면, 키-값 쌍을 해시에 추가하거나 삭제하고, 특정 키에 대한 값을 조회하는 등의 작업을 할 수 있습니다.
6. opsForGeo(): 이 메소드는 Redis의 Geo(지리적 위치) 타입의 데이터에 대한 작업을 수행합니다. 예를 들면, 지리적 위치 정보를 추가하거나, 특정 지점에서 일정 범위 내에 있는 요소들을 조회하는 작업을 할 수 있습니다.
7. opsForHyperLogLog(): 이 메소드는 Redis의 HyperLogLog 타입의 데이터에 대한 작업을 수행합니다. HyperLogLog는 중복을 허용하지 않는 유니크한 요소의 개수를 추정하기 위한 알고리즘입니다.
 



--- 
## JPA
* 
---
## WebClient


