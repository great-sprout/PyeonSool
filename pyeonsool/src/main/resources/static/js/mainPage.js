function addRecommendSwitchEvent() {
  const monthTitle = document.querySelector(".recommend__month-title");
  const recommendListMonth = document.querySelector(".recommend__list--month");
  const yoursTitle = document.querySelector(".recommend__yours-title");
  const recommendListYours = document.querySelector(".recommend__list--yours");
  const SELECTED_TITLE = "recommend__selected-title";
  
  monthTitle.addEventListener("click", function(event){
    monthTitle.classList.add(SELECTED_TITLE);
    yoursTitle.classList.remove(SELECTED_TITLE);
  
    recommendListMonth.classList.remove("unvisible");
    recommendListYours.classList.add("unvisible");
  });
  
  yoursTitle.addEventListener("click", function(event){
    monthTitle.classList.remove(SELECTED_TITLE);
    yoursTitle.classList.add(SELECTED_TITLE);
  
    recommendListMonth.classList.add("unvisible");
    recommendListYours.classList.remove("unvisible");
  });
}

function addMonthRecommendSlideEvent() {
  const monthRecommendUlItems = document.querySelector(".recommend__list--month .recommend__items");
  const IMAGE_COUNT = monthRecommendUlItems.childElementCount;
  const SLIDE_DISTANCE = 1140;
  const SLIDE_IMAGE_COUNT = 4;
  const leftSlideButton = document.querySelector(".recommend__list--month .slide-button--left");
  const rightSlideButton = document.querySelector(".recommend__list--month .slide-button--right");
  
  const monthRecommendSlideController = new SlideController(0, monthRecommendUlItems, IMAGE_COUNT, SLIDE_IMAGE_COUNT,  SLIDE_DISTANCE, leftSlideButton, rightSlideButton);
  monthRecommendSlideController.controlButtons();

  leftSlideButton.addEventListener("click", function(event) {
    monthRecommendSlideController.controlButtonSlide(event.target);

    changeSelectedCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, monthRecommendSlideController.index);
  });
  
  rightSlideButton.addEventListener("click", function(event) {
    monthRecommendSlideController.controlButtonSlide(event.target);

    changeSelectedCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, monthRecommendSlideController.index);
  });

  createSlideCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT);
}

function createSlideCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT) {
  const circleCount = IMAGE_COUNT / SLIDE_IMAGE_COUNT;
  for (let i = 0; i < circleCount; i++) {
    const div = document.createElement("div");
    div.className = "recommend__count-circle";

    if (i == 0) {
      div.classList.add("recommend__count-circle--selected");
    }

    document.querySelector(".recommend__list--month .recommend__count-circles").appendChild(div);
  }
}

function changeSelectedCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, index) {
  const countCircle = document.querySelectorAll(".recommend__count-circle");
  const circleCount = IMAGE_COUNT / SLIDE_IMAGE_COUNT;

  for (let i = 0; i < circleCount; i++) {
    if (i == index) {
      countCircle[i].classList.add("recommend__count-circle--selected");
    } else {
      countCircle[i].classList.remove("recommend__count-circle--selected");
    }
  }
}

addRecommendSwitchEvent();
addMonthRecommendSlideEvent();