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

addRecommendSwitchEvent();