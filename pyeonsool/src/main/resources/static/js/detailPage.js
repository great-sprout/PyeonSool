function addRecommendationSlidingEvent() {
  const alcoholRecommendationItems = document.querySelector(".alcohol-recommendation__items");
  const IMAGE_COUNT = alcoholRecommendationItems.childElementCount;
  const SLIDE_DISTANCE = 1190;
  const leftSlideButton = document.querySelector(".slide-button--left");
  const rightSlideButton = document.querySelector(".slide-button--right");

  const recommendationSlideController = new SlideController(10, alcoholRecommendationItems, IMAGE_COUNT, 5,  SLIDE_DISTANCE, leftSlideButton, rightSlideButton);
  recommendationSlideController.controlButtons();

  leftSlideButton.addEventListener("click", function(event) {
    recommendationSlideController.controlButtonSlide(event.target);
  });

  rightSlideButton.addEventListener("click", function(event) {
    recommendationSlideController.controlButtonSlide(event.target);
  });
}

function addLikeClickEvent() {
  const alcoholInfoLikeButton = document.querySelector(".alcohol-info__like-button");

  alcoholInfoLikeButton.addEventListener("click", function() {
    const LIKE_STATUS_CLASS=  "alcohol-info__like-button--liked";
    if (alcoholInfoLikeButton.classList.contains(LIKE_STATUS_CLASS)) {
      // dislike ajax
      const request = new XMLHttpRequest();

      if (!request) {
        alert("XMLHTTP 인스턴스 생성 불가");
        return false;
      }

      request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
          if (request.status === 200) {
            alcoholInfoLikeButton.classList.remove(LIKE_STATUS_CLASS);
          } else {
            alert(request.response.message);
          }
        }
      }

      request.open("post", window.location.pathname + "/dislike");
      request.responseType = "json";
      request.send();
    } else {
      // like ajax
      const request = new XMLHttpRequest();

      if (!request) {
        alert("XMLHTTP 인스턴스 생성 불가");
        return false;
      }

      request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
          if (request.status === 200) {
            alcoholInfoLikeButton.classList.add(LIKE_STATUS_CLASS);
          } else {
            alert(request.response.message);
          }
        }
      }

      request.open("post", window.location.pathname + "/like");
      request.responseType = "json";
      request.send();
    }
  })
}

function likeAlcoholAjax() {

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

function addReviewEditStateEvent() {
  document.querySelector(".reviews").addEventListener("click", function(event) {
    const review = event.target.closest(".review");
    if (review === null) {
      return;
    }

    const reviewContent = review.querySelector(".review__content");
    const reviewTextarea = review.querySelector(".review__textarea");
    const reviewButtons = review.querySelector(".review__buttons");
    const reviewEditor = review.querySelector(".review__editor");
    const fixedStars = review.querySelector(".review__fixed-stars");
    const revieStars = review.querySelector(".review__stars");

    if (event.target.classList.contains("review__edit-status")) {
      // 수정 가능 상태
      reviewTextarea.textContent = reviewContent.innerText;

      fixedStars.classList.add("unvisible");
      revieStars.classList.remove("unvisible");
      reviewContent.classList.add("unvisible");
      reviewTextarea.classList.remove("unvisible");
      reviewButtons.classList.add("unvisible");
      reviewEditor.classList.remove("unvisible");

    } else if (event.target.classList.contains("review__cancel")) {
      // 수정 불가능 상태
      fixedStars.classList.remove("unvisible");
      revieStars.classList.add("unvisible");
      reviewContent.classList.remove("unvisible");
      reviewTextarea.classList.add("unvisible");
      reviewButtons.classList.remove("unvisible");
      reviewEditor.classList.add("unvisible");
    }

  });
}

addLikeClickEvent();
addReviewRecommendationEvent();
addRecommendationSlidingEvent();
addReviewEditStateEvent();
