function addKeywordChoiceModalEvent() {
  const UNVISIBLE = "unvisible";
  document.querySelector(".sign-up__submit").addEventListener("click", function() {
    const password = document.querySelector(".sign-up__password-input").value;
    const passwordConfirm = document.querySelector(".sign-up__password-confirm-input").value;

    if (password !== passwordConfirm) {
      document.querySelector(".sign-up__password-confirm-wrap .errors").classList.remove("unvisible");
      return;
    } else {
      document.querySelector(".sign-up__password-confirm-wrap .errors").classList.add("unvisible");
    }

    document.querySelector(".modal").classList.remove(UNVISIBLE);
  });

  document.querySelector(".keyword-choice__confirm").addEventListener("click", function() {
    
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

    document.querySelector(".modal").classList.add(UNVISIBLE);
  });
}

addKeywordChoiceModalEvent();