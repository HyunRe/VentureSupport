# VentureSupport : 유통 자영업자들 전용 물류 운송 스케줄링 앱

## 1. 프로젝트 목적

1.1 **프로젝트 정의**

  본 프로젝트를 통해 제안하고자 하는 [Venture Support]은 유통업자를 위한 운송 스케줄 정보를 제공하는 애플리케이션이다. 이를 위해 네이버 지도 API(Application Programming Interface)를 이용한 특정 지역의 지도 정보와 운송 동선에 맞게 설계된 운송 스케줄러, 네이버 페이 API(Application Programming Interface)를 이용한 수입/지출 통계를 기반으로 유통업자에게 물류 운송을 위한 편의성을 제공하고자 한다.
  

1.2 **프로젝트 배경**

  유통업자들이 물류 운송을 위해 주로 사용하게 되는 화물 앱(아틀란트럭, 샌디 등), 내비게이션(티맵, 카카오내비 등), 사업자용 은행 앱(신한은행, 기업은행 등) 등을 사용하여 운송하는 과정을 본 적이 있다. 화물 앱을 통해 화물 오더를 받을 수 있고, 내비게이션을 사용해 길 찾기와 교통상황 정보를 받을 수 있으며, 은행 앱으로 수입과 지출, 매출을 알 수 있다. 물론 앱 각각의 특성을 살려 따로 사용할 수도 있지만, 운송 중에는 앞서 말한 여러 앱을 동시에 사용하기엔 다소 불편한 점이 있다. 따라서 본 프로젝트에서는 다음에 제시한 기능을 제공하고자 한다.

- **물류 운송 경로 최적화, 스케줄 관리, 매출 현황 통계를 중점으로 운송 진행 순서와 사용 관점에서 편의성 제공**
- **네이버 지도 API(Application Programming Interface)를 이용한 지도를 통해 거래처 정보와 창고 정보를 얻어 유통업자에게 운송 동선을 제공하고 그에 따른 운송 스케줄러 제공**
- **네이버 페이 API(Application Programming Interface)를 이용하여 유통업자 수입과 지출에 대한 데이터를 분석한다. 이때, 자신의 매출을 일일과 월간 방식으로 통계를 차트를 통해 시각적으로 제공**

  유통업자들에게 네이버 지도 API(Application Programming Interface)를 이용한 지도와 운송 동선에 맞게 설계된 운송 스케줄러와 네이버 페이 API(Application Programming Interface)를 이용한 수입과 지출을 보여주는 통계를 기반 앱을 제공함으로써 운송 과정에 있어서 불편함을 줄여줄 수 있을 것이다.


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

이용자의 업종을 선할 수 있는 화면

물류 운송 건을 공고 및 분배하는 업체 이용자와 공고된 운송 건을 선택 및 운반을 담당하는 운송기사로 구분한다.


- **물류 운송 내역 작성 UI**

업체 이용자가 물류 운송 내역을 작성할 수 있는 화면

업체 이용자는 일자, 거래처 정보, 거래처 위치, 상품 정보, 상품 수량, 총금액을 작성한다. 이 정보는 홈 화면에서 물류 운송 건 목록으로 나타나게 된다.


- **홈 UI**

공고된 운송 건을 보고 선택할 수 있는 홈 화면

업체 이용자가 작성한 물류 운송 건 목록이 나열되어 있다. 물류 운송 건 선택하면 세부적인 내용을 볼 수 있다. 운송기사는 자신이 원하는 물류 운송 건을 선택할 수 있으며, 반드시 2개 이상을 선택해야 한다.


- **지도 UI**

운송 동선을 계획할 수 있는 지도 화면

네이버 지도 API(Application Programming Interface)를 통해 선택한 물류 운송 건에 있는 거래처 위치와 창고 위치가 지도에 표시된다. 운송 기사는 거래처나 창고를 클릭하면 현재 위치에서 해당 거래처 또는 창고 위치까지의 운송 동선을 구성할 수 있다. 차량 재고 현황과 새 물류 운송 건 공고의 추가에 따라 새롭게 운송 동선을 다시 구성할 수 있다.


- **스케줄러 UI**

계획된 운송 동선에 따라 보여지는 스케줄러 화면

운송기사가 지도 UI에서 구성한 동선에 따라 스케줄러 화면에 창고와 현재 운송해야 하는 거래처의 정보와 상품 정보, 수량, 시간이 차례대로 나타나게 된다. 완료된 거래처는 ‘운송 완료’ 버튼으로 삭제할 수 있으며, 새 물류 운송 건이 추가되면 동선에 맞게 스케줄러가 초기화된다. 차량 재고 현황을 통해 동선을 수정해도 된다.


- **내 정보 UI**

자신의 매출을 기반으로 통계자료를 보여주는 내 정보 화면

네이버 페이 API(Application Programming Interface)와 연동하여 물류 운송 내역으로 받은 수입과 운송 중 발생한 지출 내역을 통해 일일 매출과 월간 매출을 보여준다. 월간 지출 내역을 보여준다.




2.3. **기대 효과**

2.4. **관련 기술**

2.5. **개발 도구**
