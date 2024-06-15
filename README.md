# VentureSupport

유통 자영업자 전용 물류 운송 스케줄링 앱

## 1. 프로젝트 목적

1.1 **프로젝트 정의**

  본 프로젝트를 통해 제안하고자 하는 [Venture Support]은 유통업자를 위한 운송 스케줄 정보를 제공하는 애플리케이션이다. 이를 위해 네이버 지도 API(Application Programming Interface)를 이용한 특정 지역의 지도 정보와 운송 동선에 맞게 설계된 운송 스케줄러, 네이버 페이 API(Application Programming Interface)를 이용한 수입/지출 통계를 기반으로 유통업자에게 물류 운송을 위한 편의성을 제공하고자 한다.
  

1.2 **프로젝트 배경**

  유통업자들이 물류 운송을 위해 주로 사용하게 되는 화물 앱(아틀란트럭, 샌디 등), 내비게이션(티맵, 카카오내비 등), 사업자용 은행 앱(신한은행, 기업은행 등) 등을 사용하여 운송하는 과정을 본 적이 있다. 화물 앱을 통해 화물 오더를 받을 수 있고, 내비게이션을 사용해 길 찾기와 교통상황 정보를 받을 수 있으며, 은행 앱으로 수입과 지출, 매출을 알 수 있다. 물론 앱 각각의 특성을 살려 따로 사용할 수도 있지만, 운송 중에는 앞서 말한 여러 앱을 동시에 사용하기엔 다소 불편한 점이 있다. 따라서 본 프로젝트에서는 다음에 제시한 기능을 제공하고자 한다.

- **물류 운송 경로 최적화, 스케줄 관리, 매출 현황 통계를 중점으로 운송 진행 순서와 사용 관점에서 편의성 제공**
- **네이버 지도 API(Application Programming Interface)를 이용한 지도를 통해 거래처 정보와 창고 정보를 얻어 유통업자에게 운송 동선을 제공하고 그에 따른 운송 스케줄러 제공**
- **네이버 페이 API(Application Programming Interface)를 이용하여 유통업자 수입과 지출에 대한 데이터를 분석한다. 이때, 자신의 매출을 일일과 월간 방식으로 통계를 차트를 통해 시각적으로 제공**

  
1.3 **프로젝트 목표**

가. 운송 동선 최적화 제공
  - 네이버 지도 API(Application Programming Interface)를 이용한 지도를 통해 거래처 정보와 창고 정보를 얻어 유통업자에게 거리 혹은 시간, 경로에 따라 최적화된 운송 동선을 재공하도록 구현한다.

나. 스케줄 관리의 편의성 제공
  - 최적화된 운송 동선에 따른 운송 스케줄러를 제공하도록 구현한다.

다. 수입과 지출에 대한 매출 통계자료 제공
  - 네이버 페이 API(Application Programming Interface)를 이용하여 자신의 지출에 대한 데이터 분석을 통해 유통업자에게 자신의 매출을 기반으로 통계자료의 시각화 서비스를 제공할 수 있도록 구현한다.



## 2. 프로젝트 개요

2.1 **프로젝트 구조**

![image](https://github.com/HyunRe/VentureSupport/assets/130357067/65255ea2-2626-4b58-8a5e-130790651227)

2.2. **예상 결과물**

- **이용자 선택 UI**
 
![회원가입](https://github.com/HyunRe/VentureSupport/assets/130357067/e5381773-68d1-4d45-8a2e-57508acd0f1a)


이용자의 업종을 선택할 수 있는 화면

물류 운송 건을 공고 및 분배하는 업체 이용자와 공고된 운송 건을 선택 및 운반을 담당하는 운송기사로 구분한다.


- **다양한 로그인 지원**

![다양한 로그인 지원](https://github.com/HyunRe/VentureSupport/assets/130357067/2169515e-7863-4e46-b5d4-21c79602670f)


![로그인](https://github.com/HyunRe/VentureSupport/assets/130357067/ccd97f55-1890-44ec-9988-687549f09f5e)

자체 로그인

![네이버 로그인](https://github.com/HyunRe/VentureSupport/assets/130357067/a0052200-c456-41d2-a516-07a94508fc00)


네이버 연동 로그인
<figure class="thrid"> 
  <a href="link"><img src="![다양한 로그인 지원](https://github.com/HyunRe/VentureSupport/assets/130357067/2169515e-7863-4e46-b5d4-21c79602670f)"></a> 
  <a href="link"><img src="![로그인](https://github.com/HyunRe/VentureSupport/assets/130357067/ccd97f55-1890-44ec-9988-687549f09f5e)"></a>
  <a href="link"><img src="![네이버 로그인](https://github.com/HyunRe/VentureSupport/assets/130357067/a0052200-c456-41d2-a516-07a94508fc00)"></a>
<figcaption>자체로그인 네이버 연동 로그인</figcaption></figure>

로그인 후 이용자 업종에 최적화된 서로 다른 기능을 지원한다.


- **물류 운송 내역 작성 UI**

![운송내역작성](https://github.com/HyunRe/VentureSupport/assets/130357067/ffe8a669-1700-41b6-8920-3c15a07f2f03)


업체 이용자가 물류 운송 내역을 작성할 수 있는 화면

업체 이용자는 일자, 거래처 정보, 거래처 위치, 상품 정보, 상품 수량, 총금액을 작성한다. 이 정보는 홈 화면에서 물류 운송 건 목록으로 나타나게 된다.


- **홈 UI**

![공고된 물류 운송](https://github.com/HyunRe/VentureSupport/assets/130357067/cbb952b4-400b-419c-a249-488a31baa3f2)

공고된 물류 운송 건

![선택한 물류 운송건](https://github.com/HyunRe/VentureSupport/assets/130357067/6467173d-5bde-4ffe-8979-77feb4483c56)

선택한 물류 운송 건

![내물류운송건](https://github.com/HyunRe/VentureSupport/assets/130357067/a8cc7b91-2737-43e3-a1b0-4344a4b641ae)

선택된 모든 물류 운송 목

공고된 운송 건을 보고 선택할 수 있는 홈 화면

업체 이용자가 작성한 물류 운송 건 목록이 나열되어 있다. 물류 운송 건을 선택하면 세부적인 내용을 볼 수 있다. 운송기사는 자신이 원하는 물류 운송 건을 선택할 수 있으며, 반드시 2개 이상을 선택해야 한다.


- **지도 UI**

![내 창고 위치](https://github.com/HyunRe/VentureSupport/assets/130357067/0c5f7858-fd08-4310-b3e9-ab4119b0b87e)

창고 위치

![지도](https://github.com/HyunRe/VentureSupport/assets/130357067/c67826dd-c06c-44cb-a86e-b8b5d514857f)

운송기사가 운송 동선을 계획할 수 있는 지도 화면

네이버 지도 API(Application Programming Interface)를 통해 선택한 물류 운송 건에 있는 거래처 위치와 창고 위치가 지도에 표시된다. 운송 기사는 거래처나 창고를 클릭하면 현재 위치에서 해당 거래처 또는 창고 위치까지의 운송 동선을 구성할 수 있다. 차량 재고 현황과 새 물류 운송 건 공고의 추가에 따라 새롭게 운송 동선을 다시 구성할 수 있다.


- **스케줄러 UI**

![스케줄러](https://github.com/HyunRe/VentureSupport/assets/130357067/aec60ac0-48f1-438b-a230-901af99590bf)


운송기사가 계획한한 운송 동선에 따라 보여지는 스케줄러 화면

운송기사가 지도 UI에서 구성한 동선에 따라 스케줄러 화면에 창고와 현재 운송해야 하는 거래처의 정보와 상품 정보, 수량, 시간이 차례대로 나타나게 된다. 완료된 거래처는 ‘운송 완료’ 버튼으로 삭제할 수 있으며, 새 물류 운송 건이 추가되면 동선에 맞게 스케줄러가 초기화된다. 차량 재고 현황을 통해 동선을 수정해도 된다.



- **내 정보 UI**

![내프로필](https://github.com/HyunRe/VentureSupport/assets/130357067/76d43518-feb9-4de8-be4c-8199ee1539df)

내 프로필

![프로필 관리](https://github.com/HyunRe/VentureSupport/assets/130357067/81b8e9c6-2425-4fdc-989a-eb74ddfabd33)

내 프로필 관리

![프로필 수정](https://github.com/HyunRe/VentureSupport/assets/130357067/5a108839-3009-438e-9f13-bc3073983c6d)

내 프로필

**내 프로필 관련 화면**

![수입](https://github.com/HyunRe/VentureSupport/assets/130357067/f15c6d50-f5b4-474d-8a41-c33bea7c1b6e)

수입 통계

![지출](https://github.com/HyunRe/VentureSupport/assets/130357067/9664b11b-c31b-4278-9b45-ac9bb71aab3d)

지출 통계

![매출](https://github.com/HyunRe/VentureSupport/assets/130357067/605dc364-2c56-4bce-aca1-acd736a6d51b)

매출 통계

**통계 관련 화면**

![네이버 페이1](https://github.com/HyunRe/VentureSupport/assets/130357067/445edad1-8672-466c-980c-4d128e82c55a)

네이버 페이

![네이버 페이 결제](https://github.com/HyunRe/VentureSupport/assets/130357067/705f4e29-d7f6-4d6e-bbab-e48154aca34e)

네이버 페이 결제

**네이버 페이 관련 화면**

운송기사의 매출을 기반으로 통계자료를 보여주는 내 정보 화면

네이버 페이 API(Application Programming Interface)와 연동하여 물류 운송 내역으로 받은 수입과 운송 중 발생한 지출 내역을 통해 일일 매출과 월간 매출을 보여준다. 네이버 페이 결제 시스템과 연동해, 해당 결제 수단(PG)에 대한 월간 지출 내역을 보여준다.




2.3. **기대 효과**

유통업자들에게 네이버 지도 API(Application Programming Interface)를 이용한 지도와 운송 동선에 맞게 설계된 운송 스케줄러와 네이버 페이 API(Application Programming Interface)를 이용한 수입과 지출을 보여주는 통계를 기반 앱을 제공함으로써 운송 과정에 있어서 불편함을 줄여줄 수 있을 것이다.


2.4. **관련 기술**

- **OAuth**
  
OAuth("Open Authorization")는 인터넷 사용자들이 비밀번호를 제공하지 않고 다른 웹사이트 상의 자신들의 정보에 대해 웹사이트나 애플리케이션의 접근 권한을 부여할 수 있는 공통적인 수단으로서 사용되는, 접근 위임을 위한 개방형 표준이다. 이 매커니즘은 여러 기업들에 의해 사용되는데, 이를테면 아마존, 구글, 페이스북, 마이크로소프트, 트위터가 있으며 사용자들이 타사 애플리케이션이나 웹사이트의 계정에 관한 정보를 공유할 수 있게 허용한다.

- **API(Application Programming Interface)**
  
API(application programming interface), 응용 프로그램 프로그래밍 인터페이스)는 컴퓨터나 컴퓨터 프로그램 사이의 연결이다. 일종의 소프트웨어 인터페이스이며 다른 종류의 소프트웨어에 서비스를 제공한다. 이러한 연결이나 인터페이스를 빌드하거나 사용하는 방법을 기술하는 문서나 표준은 API(application programming interface) 규격(사양)으로 부른다. 이 표준을 충족하는 컴퓨터 시스템은 API(application programming interface)가 구현(implement)되었다거나 노출(expose)되었다고 말한다. API(application programming interface)[10]라는 용어는 사양이나 구현체를 의미할 수 있다.

- **Cloud Computing**
  
클라우드 컴퓨팅(Cloud Computing)은 IT 리소스를 인터넷을 통해 온디맨드로 제공하고 사용한 만큼만 비용을 지불하는 것을 말한다. 물리적 데이터 센터와 서버를 구입, 소유 및 유지 관리하는 대신, Amazon Web Services(AWS)와 같은 클라우드 공급자로부터 필요에 따라 컴퓨팅 파워, 스토리지, 데이터베이스와 같은 기술 서비스에 액세스할 수 있다.


2.5. **개발 도구**


- **코틀린**
  
오픈 소스 프로그래밍 언어. 안드로이드, 스프링 프레임워크, Tomcat, JavaScript, Java EE, HTML5, iOS, 라즈베리 파이 등을 개발할 때 사용할 수 있다. 본 프로젝트에서는 안드로이드 개발에 사용되었다.

- **AWS EC2**
  
아마존 일래스틱 컴퓨트 클라우드(Amazon Elastic Compute Cloud, EC2)는 아마존닷컴의 클라우드 컴퓨팅 플랫폼 아마존 웹 서비스의 중앙부를 이루며, 사용자가 가상 컴퓨터를 임대 받아 그 위에 자신만의 컴퓨터 애플리케이션들을 실행할 수 있게 한다. EC2는 사용자가 아마존 머신 이미지(AMI)로 부팅하여 아마존이 "인스턴스"라 부르는 가상 머신을, 원하는 소프트웨어를 포함하여 구성할 수 있게 하는 웹 서비스를 제공함으로써 스케일링이 가능한 애플리케이션 배치(deployment)를 장려한다. 사용자는 필요하면 서버 인스턴스를 만들고 시작하고 종료할 수 있으며, 실행 중인 서버에 대해 시간 당 지불하므로 "일래스틱"(elastic, 탄력적인)이라는 용어를 사용하게 된다. EC2는 사용자에게 레이턴시 최적화와 높은 수준의 다중화를 허용하는 지리학적 인스턴스 위치에 대한 통제 기능을 제공한다.

- **Spring Boot**
  
스프링 프레임워크(Spring Framework)는 자바 플랫폼을 위한 오픈 소스 애플리케이션 프레임워크로서 간단히 스프링(Spring)이라고도 한다. 동적인 웹 사이트를 개발하기 위한 여러 가지 서비스를 제공하고 있다. 대한민국 공공기관의 웹 서비스 개발 시 사용을 권장하고 있는 전자정부 표준프레임워크의 기반 기술로 쓰이고 있다.

- **AWS RDS**
  
아마존 관계형 데이터베이스 서비스(Amazon Relational Database Service) 또는 아마존 RDS(Amazon RDS)는 아마존 웹 서비스(AWS)가 서비스하는 분산 관계형 데이터베이스이다. 애플리케이션 내에서 관계형 데이터베이스의 설정, 운영, 스케일링을 단순케 하도록 설계된 클라우드 내에서 동작하는 웹 서비스이다. 데이터베이스 소프트웨어를 패치하거나 데이터베이스를 백업하거나 시점 복구를 활성화하는 것과 같은 복잡한 관리 프로세스들은 자동으로 관리된다. 스토리지와 연산 자원들을 스케일링하는 것은 하나의 API 호출로 수행할 수 있다.

- **MySQL**
  
MySQL은 세계에서 가장 많이 쓰이는 오픈 소스의 관계형 데이터베이스 관리 시스템(RDBMS)이다. 다중 스레드, 다중 사용자, 구조질의어 형식의 데이터베이스 관리 시스템으로 오라클이 관리 및 지원하고 있으며, Qt처럼 이중 라이선스가 적용된다. 하나의 옵션은 GPL이며, GPL 이외의 라이선스로 적용시키려는 경우 전통적인 지적재산권 라이선스의 적용을 받는다.
