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

  alcoholInfoLikeButton.addEventListener("click", function() {
    alcoholInfoLikeButton.classList.toggle("alcohol-info__like-button--liked");
  })
}

function addReviewRecommendationEvent() {
  document.querySelector(".reviews").addEventListener("click", function(event) {
    const target = event.target;

    if (target.classList.contains("recommendation__icon")) {
      clearClickeState(target, ".no-recommendation__icon", ".no-recommendation__icon--clicked");
      changeToClickState(target);

    } else if (target.classList.contains("recommendation__icon--clicked")) {
      changeToUnclickState(target);

    } else if (target.classList.contains("no-recommendation__icon")) {
      clearClickeState(target, ".recommendation__icon", ".recommendation__icon--clicked");
      changeToClickState(target);
      
    } else if (target.classList.contains("no-recommendation__icon--clicked")) {
      changeToUnclickState(target);
    }
  });

  function changeToClickState(target) {
    target.classList.add("unvisible");
    target.nextElementSibling.classList.remove("unvisible");
  }
  
  function changeToUnclickState(target) {
    target.previousElementSibling.classList.remove("unvisible");
    target.classList.add("unvisible");
  }
  
  function clearClickeState(target, unclickStateSelector, clickStateSelector) {
    const reviewPreference = target.closest(".review__preference");
  
    reviewPreference.querySelector(unclickStateSelector).classList.remove("unvisible");
    reviewPreference.querySelector(clickStateSelector).classList.add("unvisible");
  }
}

document.querySelector(".reviews").addEventListener("click", function(event) {
  
  if (event.target.classList.contains("readonly")) {
    event.preventDefault();
  }
})

addLikeClickEvent();
addReviewRecommendationEvent();
addRecommendationSlidingEvent();
