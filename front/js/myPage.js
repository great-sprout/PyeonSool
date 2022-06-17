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
    const  mystyleSlideController = new MyPageSildeController(10, mypageMystyleItems, IMAGE_COUNT, SLIDE_DISTANCE, leftSlideButton, rightSlideButton);
                                                    //initLeft, imageBox, imageCount, slideDistance, prevButton, nextButton
                                                    //this.index += (selectedButton === this.prevButton) ? -1 : 1;
                                                    //this.imageBox.style.left = 10 -( 0* 1190) + "px"; //초기상태 10px
                                                    //this.imageBox.style.left = 10 -( 1* 1190) + "px"; //nextButton클릭시 -1180px
    //addEventListener메소드
    //ex)leftSlideButton.addEventListener 해석하면
    //leftSlideButton라는 문서 객체의 click 이벤트가 발생했을 때,
    //매개변수로 지정한 콜백 함수를 실행해라는 의미
    //즉 click은 이벤트명이다. function(event)은 함수이다.
    leftSlideButton.addEventListener("click", function(event) {
       //event.target은 이벤트가 발생한 요소를 반환해줌
        mystyleSlideController.slide(event.target);
    });
    
    rightSlideButton.addEventListener("click", function(event) {
        mystyleSlideController.slide(event.target);
    });
  }

  
  //호출
  addMystyleSlidingEvent();