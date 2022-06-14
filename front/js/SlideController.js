// 이미지를 감싸고 있는 imageBox 엘리먼트 속성이 position: absolute이어야 사용 가능
class SlideController {
  constructor(initLeft, imageBox, imageCount, slideDistance, prevButton, nextButton) {
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

  slide(selectedButton) {
      this.index += (selectedButton === this.prevButton) ? -1 : 1;
      this.imageBox.style.left = this.initLeft -(this.index * this.slideDistance) + "px";
      this.controlButtons();
  }

  controlButtons() {
      if (this.isFirstPage()) {
          this.hidePrevButton();
      } else {
          this.showPrevButton();
      }

      if (this.isLastPage()) {
          this.hideNextButton();
      } else {
          this.showNextButton();
      }
  }

  isFirstPage() {
      return this.index <= 0;
  }

  isLastPage() {
      return this.index >= this.imageCount - 1;
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