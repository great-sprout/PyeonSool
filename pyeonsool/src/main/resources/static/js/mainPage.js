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

    changeSelectedCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, monthRecommendSlideController.index, ".recommend__list--month");
  });
  
  rightSlideButton.addEventListener("click", function(event) {
    monthRecommendSlideController.controlButtonSlide(event.target);

    changeSelectedCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, monthRecommendSlideController.index, ".recommend__list--month");
  });

  createSlideCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, ".recommend__list--month");
}

function addYoursRecommendSlideEvent() {
  const monthRecommendUlItems = document.querySelector(".recommend__list--yours .recommend__items");
  const IMAGE_COUNT = monthRecommendUlItems.childElementCount;
  const SLIDE_DISTANCE = 1140;
  const SLIDE_IMAGE_COUNT = 4;
  const leftSlideButton = document.querySelector(".recommend__list--yours .slide-button--left");
  const rightSlideButton = document.querySelector(".recommend__list--yours .slide-button--right");
  
  const yoursRecommendSlideController = new SlideController(0, monthRecommendUlItems, IMAGE_COUNT, SLIDE_IMAGE_COUNT,  SLIDE_DISTANCE, leftSlideButton, rightSlideButton);
  yoursRecommendSlideController.controlButtons();

  leftSlideButton.addEventListener("click", function(event) {
    yoursRecommendSlideController.controlButtonSlide(event.target);

    changeSelectedCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, yoursRecommendSlideController.index, ".recommend__list--yours");
  });
  
  rightSlideButton.addEventListener("click", function(event) {
    yoursRecommendSlideController.controlButtonSlide(event.target);

    changeSelectedCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, yoursRecommendSlideController.index, ".recommend__list--yours");
  });

  createSlideCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, ".recommend__list--yours");
}

function createSlideCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, ROOT_CLASS) {
  const circleCount = IMAGE_COUNT / SLIDE_IMAGE_COUNT;
  for (let i = 0; i < circleCount; i++) {
    const div = document.createElement("div");
    div.className = "recommend__count-circle";

    if (i == 0) {
      div.classList.add("recommend__count-circle--selected");
    }

    document.querySelector(ROOT_CLASS + " .recommend__count-circles").appendChild(div);
  }
}

function changeSelectedCircle(IMAGE_COUNT, SLIDE_IMAGE_COUNT, index, ROOT_CLASS) {
  const countCircle = document.querySelectorAll(ROOT_CLASS + " .recommend__count-circle");
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
addYoursRecommendSlideEvent();

const monthTitle = document.querySelector(".recommmend__month-title");
const recommendListMonth = document.querySelector(".recommend__list--month");
const yoursTitle = document.querySelector(".recommend__yours-title");
const recommendListYours = document.querySelector(".recommend__list--yours");
const SELECTED_TITLE = "recommmend__selected-title";

const sojuTitle = document.querySelector(".best-like__soju-title");
const bestLikeListSoju = document.querySelector(".best-like__list-soju");
const beerTitle = document.querySelector(".best-like__beer-title");
const bestLikeListBeer = document.querySelector(".best-like__list-beer");
const wineTitle = document.querySelector(".best-like__wine-title");
const bestLikeListWine = document.querySelector(".best-like__list-wine");
const BEST_SELECTED_TITLE = "best-like__selected-title";

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

sojuTitle.addEventListener("click", function(event){
  sojuTitle.classList.add(BEST_SELECTED_TITLE);
  beerTitle.classList.remove(BEST_SELECTED_TITLE);
  wineTitle.classList.remove(BEST_SELECTED_TITLE);
 
  bestLikeListSoju.classList.remove("unvisible");
  bestLikeListBeer.classList.add("unvisible");
  bestLikeListWine.classList.add("unvisible");
});

beerTitle.addEventListener("click", function(event){
  sojuTitle.classList.remove(BEST_SELECTED_TITLE);
  beerTitle.classList.add(BEST_SELECTED_TITLE);
  wineTitle.classList.remove(BEST_SELECTED_TITLE);

  bestLikeListSoju.classList.add("unvisible");
  bestLikeListBeer.classList.remove("unvisible");
  bestLikeListWine.classList.add("unvisible");
});

wineTitle.addEventListener("click", function(event){
  sojuTitle.classList.remove(BEST_SELECTED_TITLE);
  beerTitle.classList.remove(BEST_SELECTED_TITLE);
  wineTitle.classList.add(BEST_SELECTED_TITLE);

  bestLikeListSoju.classList.add("unvisible");
  bestLikeListBeer.classList.add("unvisible");
  bestLikeListWine.classList.remove("unvisible");
});
