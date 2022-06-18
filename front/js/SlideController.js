// 이미지를 감싸고 있는 imageBox 엘리먼트 속성이 position: absolute이어야 사용 가능
class SlideController {
  constructor(initLeft, imageBox, imageCount, slideImageCount, slideDistance, prevButton, nextButton) {
      this.index = 0;
      this.initLeft = initLeft;
      this.slideDistance = slideDistance;
      this.imageCount = imageCount;
      this.slideImageCount = slideImageCount;
      this.prevButton = prevButton;
      this.nextButton = nextButton;
      this.imageBox = imageBox;
      this.UNVISIBLE_CLASS = "unvisible";    
  }

  controlButtonSlide(selectedButton) {
      this.index += (selectedButton === this.prevButton) ? -1 : 1;
      this.imageBox.style.left = this.initLeft -(this.index * this.slideDistance) + "px";
      this.controlButtons();
  }

  slide(selectedButton, exposedImageCount) {
    this.index += (selectedButton === this.prevButton) ? -1 : 1;

    if (this.index < 0) {
        this.index = 0;
    } else if (this.index >= this.imageCount - exposedImageCount) {
        this.index = this.imageCount - exposedImageCount;
    }

    this.imageBox.style.left = this.initLeft -(this.index * this.slideDistance) + "px";
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
      return (this.index + 1) * this.slideImageCount >= this.imageCount;
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

class BestSlideController {
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
        
        if(this.index <=this.imageCount-1 &&this.index>=0){
            this.imageBox.style.left = this.initLeft -(this.index * this.slideDistance/4) + "px";
            
        }

       
        
    }
  
    controlButtons() {
        if (this.isFirstPage()) {
            this.index=0;
        } 
        if (this.isLastPage()) {
            this.index=12;
        } 
    }
  
   
  
    isFirstPage() {
        return this.index == 0;
    }
  
    isLastPage() {
        return this.index >= this.imageCount;
    }
  
    
    
  }