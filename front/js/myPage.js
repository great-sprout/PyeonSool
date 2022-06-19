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
    recommendationSlideController.controlButtons();
   
    leftSlideButton.addEventListener("click", function(event) {
        recommendationSlideController.controlButtonSlide(event.target);
    });
    
    rightSlideButton.addEventListener("click", function(event) {
        recommendationSlideController.controlButtonSlide(event.target);
    });
  }

  //마이페이지 리뷰 수정,삭제
  function addMyReviewEditStateEvent() {
    document.querySelector(".myreviews").addEventListener("click", function(event) {
      const myreview = event.target.closest(".my-review");
      if (myreview === null) {
        return;
      }

      const myreviewContent = myreview.querySelector(".my-review__content");
      const myreviewTextarea = myreview.querySelector(".my-review__textarea");
      const myreviewButtons = myreview.querySelector(".my-review__buttons");
      const myreviewEditor = myreview.querySelector(".my-review__editor");
      const myfixedStars = myreview.querySelector(".my-review__fixed-stars");
      const myreviewStars = myreview.querySelector(".my-review__stars");
     
      
      if (event.target.classList.contains("my-review__edit-status")) {
        // 수정 가능 상태
        myreviewTextarea.textContent =myreviewContent.innerText;
       
        myfixedStars.classList.add("unvisible");
        myreviewStars.classList.remove("unvisible");
        myreviewContent.classList.add("unvisible");
        myreviewTextarea.classList.remove("unvisible");
        myreviewButtons.classList.add("unvisible");
        myreviewEditor.classList.remove("unvisible");
        console.log(1);
      } else if (event.target.classList.contains("my-review__cancel")) {
        // 수정 불가능 상태
        myfixedStars.classList.remove("unvisible");
        myreviewStars.classList.add("unvisible");
        myreviewContent.classList.remove("unvisible");
        myreviewTextarea.classList.add("unvisible");
        myreviewButtons.classList.remove("unvisible");
        myreviewEditor.classList.add("unvisible");
        console.log(2);
      }
      console.log(3);
    
    });
  }


  //호출
  addMystyleSlidingEvent();
  addMyReviewEditStateEvent();
