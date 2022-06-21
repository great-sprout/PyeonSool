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