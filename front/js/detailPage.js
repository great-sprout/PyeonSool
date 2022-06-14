function addRecommendationSlidingEvent() {
  const alcoholRecommendationItems = document.querySelector(".alcohol-recommendation__items");
  const IMAGE_COUNT = alcoholRecommendationItems.childElementCount;
  const SLIDE_DISTANCE = 1190;
  const leftSlideButton = document.querySelector(".slide-button--left");
  const rightSlideButton = document.querySelector(".slide-button--right");
  
  const recommendationSlideController = new SlideController(10, alcoholRecommendationItems, IMAGE_COUNT, SLIDE_DISTANCE, leftSlideButton, rightSlideButton);
  
  leftSlideButton.addEventListener("click", function(event) {
    recommendationSlideController.slide(event.target);
  });
  
  rightSlideButton.addEventListener("click", function(event) {
    recommendationSlideController.slide(event.target);
  });
}

function addLikeClickEvent() {
  const alcoholInfoLikeButton = document.querySelector(".alcohol-info__like-button");

  alcoholInfoLikeButton.addEventListener("click", function(event) {
    alcoholInfoLikeButton.classList.toggle("alcohol-info__like-button--liked");
  })
}

addLikeClickEvent();
addRecommendationSlidingEvent();
