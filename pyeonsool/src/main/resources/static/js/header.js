const UNVISIBLE = "unvisible";

function addHeaderNavEvent() {
  const headerNavBars = document.querySelector(".header__nav .fa-bars");
  const headerNavTimes = document.querySelector(".header__nav .fa-times");
  const listNav = document.querySelector(".list-nav");

  headerNavBars.addEventListener("click", function() {
    headerNavBars.classList.add(UNVISIBLE);
    headerNavTimes.classList.remove(UNVISIBLE);
    listNav.classList.remove(UNVISIBLE);
  });

  headerNavTimes.addEventListener("click", function() {
    headerNavBars.classList.remove(UNVISIBLE);
    headerNavTimes.classList.add(UNVISIBLE);
    listNav.classList.add(UNVISIBLE);
  });
}

function addHeaderProfileEvent() {
  document.querySelector(".fa-user-circle").addEventListener("click", function() {
    document.querySelector(".header-profile").classList.toggle(UNVISIBLE);
  });
}

function addKeywordChoiceModalEvent() {
  document.querySelector(".header-profile__select-again-keyword").addEventListener("click", function() {
    document.querySelector(".modal").classList.remove(UNVISIBLE);
  });

  document.querySelector(".keyword-choice__cancel").addEventListener("click", function() {
    document.querySelector(".modal").classList.add(UNVISIBLE);
  });

  document.querySelector(".keyword-choice__confirm").addEventListener("click", function(event) {
    const checkedKeywords = document.querySelectorAll(".keyword-choice__keyword-input:checked");

    if (checkedKeywords.length !== 3) {
      document.querySelector(".keyword-choice__choice-count").classList.add("error-keyword");
  
      event.preventDefault();
      return;
    } else {
      document.querySelector(".keyword-choice__choice-count").classList.remove("error-keyword");
    }
  });
}

addHeaderNavEvent();
addHeaderProfileEvent();
addKeywordChoiceModalEvent();