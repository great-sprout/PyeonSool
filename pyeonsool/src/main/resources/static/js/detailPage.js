function addRecommendationSlidingEvent() {
    const alcoholRecommendationItems = document.querySelector(".alcohol-recommendation__items");
    const IMAGE_COUNT = alcoholRecommendationItems.childElementCount;
    const SLIDE_DISTANCE = 1190;
    const leftSlideButton = document.querySelector(".slide-button--left");
    const rightSlideButton = document.querySelector(".slide-button--right");

    const recommendationSlideController = new SlideController(10, alcoholRecommendationItems, IMAGE_COUNT, 5, SLIDE_DISTANCE, leftSlideButton, rightSlideButton);
    recommendationSlideController.controlButtons();

    leftSlideButton.addEventListener("click", function (event) {
        recommendationSlideController.controlButtonSlide(event.target);
    });

    rightSlideButton.addEventListener("click", function (event) {
        recommendationSlideController.controlButtonSlide(event.target);
    });
}

function addLikeClickEvent() {
    const alcoholInfoLikeButton = document.querySelector(".alcohol-info__like-button");

    alcoholInfoLikeButton.addEventListener("click", function () {
        const LIKE_STATUS_CLASS = "alcohol-info__like-button--liked";
        if (alcoholInfoLikeButton.classList.contains(LIKE_STATUS_CLASS)) {
            // dislike ajax
            dislikeAlcoholAjax(LIKE_STATUS_CLASS);
        } else {
            // like ajax
            likeAlcoholAjax(LIKE_STATUS_CLASS);
        }
    })

    function dislikeAlcoholAjax(LIKE_STATUS_CLASS) {
        const request = new XMLHttpRequest();

        if (!request) {
            alert("XMLHTTP 인스턴스 생성 불가");
            return false;
        }

        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                if (request.status === 200) {
                    alcoholInfoLikeButton.classList.remove(LIKE_STATUS_CLASS);
                } else if (request.status === 401) {
                    alert("로그인 후 이용해주세요.");
                } else {
                    alert(request.response.message);
                }
            }
        }

        request.open("post", window.location.pathname + "/dislike");
        request.responseType = "json";
        request.send();
    }

    function likeAlcoholAjax(LIKE_STATUS_CLASS) {
        const request = new XMLHttpRequest();

        if (!request) {
            alert("XMLHTTP 인스턴스 생성 불가");
            return false;
        }

        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                if (request.status === 200) {
                    alcoholInfoLikeButton.classList.add(LIKE_STATUS_CLASS);
                } else if (request.status === 400) {
                    alert("Like 술은 12개까지 가능합니다.");
                } else if (request.status === 401) {
                    alert("로그인 후 이용해주세요.");
                }else {
                    alert(request.response.message);
                }
            }
        }

        request.open("post", window.location.pathname + "/like");
        request.responseType = "json";
        request.send();
    }
}

function addReviewRecommendationEvent() {
    document.querySelector(".reviews").addEventListener("click", function (event) {
        const target = event.target;
        const reviewId = event.target.closest(".review").getAttribute("number");

        if (target.classList.contains("recommendation__icon")) {
            recommendReviewAjax(target, reviewId);

        } else if (target.classList.contains("recommendation__icon--clicked")) {
            cancelRecommendReviewAjax(target, reviewId);
            // 추천 카운트 - 1
        } else if (target.classList.contains("no-recommendation__icon")) {
            notRecommendReviewAjax(target, reviewId);
            // 비추천 카운트 + 1
            // 추천 클릭되어 있었다면 추천 카운트 - 1, 아니면 그대로
        } else if (target.classList.contains("no-recommendation__icon--clicked")) {
            cancelNotRecommendReviewAjax(target, reviewId);
            // 비추천 카운트 - 1
        }
    });

    function changeLikeCount(reviewPreference, increment) {
        const likeCount = reviewPreference.querySelector(".recommendation__count");
        likeCount.textContent = parseInt(likeCount.textContent) + increment;
    }

    function changeDislikeCount(reviewPreference, increment) {
        const dislikeCount = reviewPreference.querySelector(".no-recommendation__count");
        dislikeCount.textContent = parseInt(dislikeCount.textContent) + increment;
    }

    function isClicked(reviewPreference, className) {
        return reviewPreference.querySelector(className).classList.contains("unvisible");
    }

    function recommendReviewAjax(target, reviewId) {
        const request = new XMLHttpRequest();

        if (!request) {
            alert("XMLHTTP 인스턴스 생성 불가");
            return false;
        }

        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                if (request.status === 200) {
                    // 추천 카운트 +1
                    // 비추천 클릭되어 있었다면 비추천 카운트 - 1, 아니면 그대로
                    const reviewPreference = target.closest(".review__preference");
                    changeLikeCount(reviewPreference, 1);
                    if (!isClicked(reviewPreference, ".no-recommendation__icon--clicked")) {
                        changeDislikeCount(reviewPreference, -1);
                    }

                    clearClickState(target, ".no-recommendation__icon", ".no-recommendation__icon--clicked");
                    changeToClickState(target);
                } else if (request.status === 401) {
                    alert("로그인 후 이용해주세요.");
                } else {
                    alert(request.response.message);
                }
            }
        }

        request.open("POST", "/reviews/" + reviewId + "/recommend");
        request.responseType = "json";
        request.send();
    }

    function cancelRecommendReviewAjax(target, reviewId) {
        const request = new XMLHttpRequest();

        if (!request) {
            alert("XMLHTTP 인스턴스 생성 불가");
            return false;
        }

        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                if (request.status === 200) {
                    changeLikeCount(target.closest(".review__preference"), -1);
                    changeToUnclickState(target);
                } else if (request.status === 401) {
                    alert("로그인 후 이용해주세요.");
                } else {
                    alert(request.response.message);
                }
            }
        }

        request.open("DELETE", "/reviews/" + reviewId + "/recommend");
        request.responseType = "json";
        request.send();
    }

    function notRecommendReviewAjax(target, reviewId) {
        const request = new XMLHttpRequest();

        if (!request) {
            alert("XMLHTTP 인스턴스 생성 불가");
            return false;
        }

        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                if (request.status === 200) {
                    const reviewPreference = target.closest(".review__preference");
                    changeDislikeCount(reviewPreference, 1);
                    if (!isClicked(reviewPreference, ".recommendation__icon--clicked")) {
                        changeLikeCount(reviewPreference, -1);
                    }
                    clearClickState(target, ".recommendation__icon", ".recommendation__icon--clicked");
                    changeToClickState(target);
                } else if (request.status === 401) {
                    alert("로그인 후 이용해주세요.");
                } else {
                    alert(request.response.message);
                }
            }
        }

        request.open("POST", "/reviews/" + reviewId + "/not-recommend");
        request.responseType = "json";
        request.send();
    }

    function cancelNotRecommendReviewAjax(target, reviewId) {
        const request = new XMLHttpRequest();

        if (!request) {
            alert("XMLHTTP 인스턴스 생성 불가");
            return false;
        }

        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                if (request.status === 200) {
                    changeDislikeCount(target.closest(".review__preference"), -1);
                    changeToUnclickState(target);
                } else if (request.status === 401) {
                    alert("로그인 후 이용해주세요.");
                } else {
                    alert(request.response.message);
                }
            }
        }

        request.open("DELETE", "/reviews/" + reviewId + "/not-recommend");
        request.responseType = "json";
        request.send();
    }

    function changeToClickState(target) {
        target.classList.add("unvisible");
        target.nextElementSibling.classList.remove("unvisible");
    }

    function changeToUnclickState(target) {
        target.previousElementSibling.classList.remove("unvisible");
        target.classList.add("unvisible");
    }

    function clearClickState(target, unclickStateSelector, clickStateSelector) {
        const reviewPreference = target.closest(".review__preference");

        reviewPreference.querySelector(unclickStateSelector).classList.remove("unvisible");
        reviewPreference.querySelector(clickStateSelector).classList.add("unvisible");
    }
}

function addReviewEditStateEvent() {
    document.querySelector(".reviews").addEventListener("click", function (event) {
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

function addReviewDeleteClickEvent() {
    document.addEventListener("click", function (event) {

        if (!event.target.classList.contains("review__delete")) {
            console.log(event.target)
            return;
        }

        deleteReviewAjax(event.target.closest(".review"));
    });

    function deleteReviewAjax(review) {
        const request = new XMLHttpRequest();
        if (!request) {
            alert("XMLHTTP 인스턴스 생성 불가");
            return false;
        }

        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE) {
                if (request.status === 200) {
                    window.location.reload();
                } else if (request.status === 401) {
                    alert("로그인 후 이용해주세요")
                } else {
                    alert(request.response.message);
                }
            }
        }

        request.open("DELETE", "/reviews/" + review.getAttribute("number") + "/delete")
        request.responseType = "json";
        request.send();
    }
}


addLikeClickEvent();
addReviewRecommendationEvent();
addRecommendationSlidingEvent();
addReviewEditStateEvent();
addReviewDeleteClickEvent();
