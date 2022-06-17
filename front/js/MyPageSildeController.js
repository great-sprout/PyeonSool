// 이미지를 감싸고 있는 imageBox 엘리먼트 속성이 position: absolute이어야 사용 가능
class MyPageSildeController {
    //클래스안에 생성자 선언
    // constructor안에 파라미터의 이름은 자기맘대로지음
    //파라미터값들은 클래스를 생성할때 정의해 준다.[ex)myPage.js 16번째줄 객체 생성 부분]
    constructor(initLeft, imageBox, imageCount, slideDistance, prevButton, nextButton) {
      //이름 지어준것이다.
      this.index = 0;
      this.initLeft = initLeft;
      this.slideDistance = slideDistance;
      this.imageCount = imageCount;
      this.prevButton = prevButton;
      this.nextButton = nextButton;
      this.imageBox = imageBox;
      this.UNVISIBLE_CLASS = "unvisible";

      this.controlButtons();
  }

  //클래스안에 함수
  slide(selectedButton) {
      this.index += (selectedButton === this.prevButton) ? -1 : 1; //만약 selectedButton이 prevButton와 같으면 index에 -1을 더해준다 아니면 1을 더한다.
      this.imageBox.style.left = this.initLeft -(this.index * this.slideDistance) + "px";
      this.controlButtons();
  }

  //함수
  controlButtons() {
     //슬라이드 이전 버튼 화살표 조건
      if (this.isFirstPage()) {
          this.hidePrevButton();
      } else {
          this.showPrevButton();
      }

    //슬라이드 다음 버튼 화살표 조건
      if (this.isLastPage()) {
          this.hideNextButton();
      } else {
          this.showNextButton();
      }
  }

  //첫번째 페이지인지 아닌지 알려주는 함수
  isFirstPage() {
      return this.index <= 0;
  }

  //마지막 페이지인지 아닌지 알려주는 함수
  isLastPage() {
      //return this.index >= this.imageCount - 1;
    //  console.log(this.imageCount);
    //  console.log(this.index);
    //   var a=0;
    //   a=parseInt(this.imageCount/5);
    //   if((this.imageCount%5)===0){
    //             a--;
    //     }
    //     console.log(a);
    //   return this.index == a;


     //삼항 연산자로 할시
     return this.index === (parseInt(this.imageCount/5) - (this.imageCount%5 === 0 ? 1 : 0));

     //이미지가 6개일때 인덱스는 최대 1이다.
      //이미지가 13개일때 인덱스는 최대 2이다.
      //이미지가 16일때는 인덱스는  최대3이다.
      // 1/5 =0 -> 0
      // 2/5 =0 -> 0
      // 3/5 =0 -> 0
      // 4/5 =0 -> 0
      // 5/5 =1 -> 0
      // 6/5 =1 -> 1
      // 7/5 =1 -> 1
      // 8/5 =1 -> 1
      // 9/5 =1 -> 1
      // 10/5 =2 -> 1
      // 11/5 =2 -> 2
      // 12/5 =2 -> 2
      // 13/5 =2 -> 2
      // 14/5 =2 -> 2
      // 15/5 =3 -> 2
      // 16/5 =3 -> 3
     // a/5=


  }

  hidePrevButton() {
      this.prevButton.classList.add(this.UNVISIBLE_CLASS);
  }

  hideNextButton() {
      this.nextButton.classList.add(this.UNVISIBLE_CLASS);
  }

  showPrevButton() {
      this.prevButton.classList.remove(this.UNVISIBLE_CLASS);
  }

  showNextButton() {
      this.nextButton.classList.remove(this.UNVISIBLE_CLASS);
  }
}