//자바스크립트 함수
//함수는 addMystyleSlidingEvent 이다.
//addMystyleSlidingEvent의 자료형은 function이다.
//function 부분을 부르는 게 콜백
function addMystyleSlidingEvent() {
    //document.querySelector(선택자):선택자를 읽어온다.
    //querySelector()메소드는 요소를 하나만 추출한다.
    const mypageMystyleItems = document.querySelector(".mypage-mystyle__items");
    //.childElementCount는 자식 요소의 개수를 반환하는 속성
    //바로 아래 단계에 있는 자식 요소의 개수만 세고, 자식 요소의 자식 요소의 수는 세지 않는다.
    const IMAGE_COUNT = mypageMystyleItems.childElementCount;
    const SLIDE_DISTANCE = 1190;
    const leftSlideButton = document.querySelector(".slide-button--left");
    const rightSlideButton = document.querySelector(".slide-button--right");
    
    //객체 생성
    const recommendationSlideController = new SlideController(10, mypageMystyleItems, IMAGE_COUNT, 5,  SLIDE_DISTANCE, leftSlideButton, rightSlideButton);
   
    leftSlideButton.addEventListener("click", function(event) {
        recommendationSlideController.slide(event.target);
    });
    
    rightSlideButton.addEventListener("click", function(event) {
        recommendationSlideController.slide(event.target);
    });
  }

  
  //호출
  addMystyleSlidingEvent();