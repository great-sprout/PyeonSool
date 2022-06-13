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
  document.querySelector(".header__profile").addEventListener("click", function() {
    document.querySelector(".header-profile").classList.toggle(UNVISIBLE);
  });
}

addHeaderNavEvent();
addHeaderProfileEvent();