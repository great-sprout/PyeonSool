function addKeywordChoiceModalEvent() {
  const UNVISIBLE = "unvisible";
  document.querySelector(".sign-up__submit").addEventListener("click", function() {
    document.querySelector(".modal").classList.remove(UNVISIBLE);
  });

  document.querySelector(".keyword-choice__confirm").addEventListener("click", function() {
    document.querySelector(".modal").classList.add(UNVISIBLE);
  });
}

addKeywordChoiceModalEvent();