let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();

function buildCalendar() {
  let firstDay = new Date(currentYear, currentMonth, 1);
  let lastDay = new Date(currentYear, currentMonth + 1, 0);
  let calendarTitle = document.getElementById("calendarTitle");
  let calendarBody = document.getElementById("calendarBody");
  let twoWeeksFromToday = new Date();
  twoWeeksFromToday.setDate(twoWeeksFromToday.getDate() + 14);

  calendarTitle.innerHTML = currentYear + "년 " + (currentMonth + 1) + "월";

  while (calendarBody.firstChild) {
    calendarBody.removeChild(calendarBody.firstChild);
  }

  let date = 1;
  for (let i = 0; i < 6; i++) {
    let row = document.createElement("tr");
    for (let j = 0; j < 7; j++) {
      if (i === 0 && j < firstDay.getDay()) {
        let cell = document.createElement("td");
        let cellText = document.createTextNode("");
        cell.appendChild(cellText);
        row.appendChild(cell);
      } else if (date > lastDay.getDate()) {
        break;
      } else {
        let cell = document.createElement("td");
        let cellText = document.createTextNode(date);
        if (
          date === today.getDate() &&
          currentMonth === today.getMonth() &&
          currentYear === today.getFullYear()
        ) {
          cell.classList.add("current-day");
        }
        if (
          date > today.getDate() &&
          date <= twoWeeksFromToday &&
          currentMonth === today.getMonth() &&
          currentYear === today.getFullYear()
        ) {
          cell.classList.add("two-weeks");
        }
        cell.addEventListener("click", showTimetable); // 클릭 이벤트 추가
        cell.appendChild(cellText);
        row.appendChild(cell);
        date++;
      }
    }
    calendarBody.appendChild(row);
  }
}

function prevCalendar() {
  currentYear = currentMonth === 0 ? currentYear - 1 : currentYear;
  currentMonth = currentMonth === 0 ? 11 : currentMonth - 1;
  buildCalendar();
}

function nextCalendar() {
  currentYear = currentMonth === 11 ? currentYear + 1 : currentYear;
  currentMonth = (currentMonth + 1) % 12;
  let twoWeeksFromToday = new Date();
  twoWeeksFromToday.setDate(twoWeeksFromToday.getDate() + 14);
  buildCalendar();
}
let selectedYear, selectedMonth;

function showTimetable(event) {
  let selectedDate = event.target.textContent;
  let selectedYear = currentYear;
  let selectedMonth = currentMonth + 1;
  let selected = document.getElementById("selected");
  if (selected) {
    selected.remove();
  }
  console.log(
    `Selected Date: ${selectedYear}-${selectedMonth}-${selectedDate}`
  );
  let yearParent = document.querySelector(".accordion_cont1");
  let yearDiv = document.createElement("div");
  let ymd = document.querySelector("#floatPC_daytime");
  yearDiv.classList.add("selected");
  yearDiv.id = "selected";
  ymd.innerHTML =
    "날짜 - " + selectedYear + "-" + selectedMonth + "-" + selectedDate;
  yearDiv.innerHTML =
    "Selected date: " + selectedYear + "-" + selectedMonth + "-" + selectedDate;
  yearParent.appendChild(yearDiv);
  //document.querySelector(".timetable").appendChild(yearDiv);
  document.querySelector(".timetable").style.display = "block";
  document.querySelector(".calendartable").style.display = "none";
}

buildCalendar();

let calendarBody = document.getElementById("calendarBody");
let timetable = document.querySelector(".timetable");

calendarBody.addEventListener("click", function (event) {
  if (event.target.tagName === "TD") {
    timetable.style.display = "block";
  }
});

let timetablebtn = document.querySelector(".timetable");
let timeSelect = document.querySelector('[title="시간 선택"]');
timeSelect.addEventListener("click", function () {
  if (timetablebtn.style.display === "none") {
    timetablebtn.style.display = "block";
  } else {
    timetablebtn.style.display = "none";
    uploadpagebtn.style.display = "none";
  }
});

const selectTimes = document.querySelectorAll(".select_time");

selectTimes.forEach((selectTime) => {
  selectTime.addEventListener("click", function () {
    const dateText = this.querySelector(".date").textContent;
    document.querySelector("#floatPC_time").textContent = "시간 - " + dateText;
  });
});

let uploadpagebtn = document.querySelector(".uploadimage");
let uploadSelect = document.querySelector('[title="사진 업로드"]');
uploadSelect.addEventListener("click", function () {
  if (uploadpagebtn.style.display === "none") {
    uploadpagebtn.style.display = "block";
  } else {
    uploadpagebtn.style.display = "none";
  }
});

let calendarpagebtn = document.querySelector(".calendartable");
let calendarSelect = document.querySelector('[title="날짜 선택"]');
calendarSelect.addEventListener("click", function () {
  if (calendarpagebtn.style.display === "none") {
    calendarpagebtn.style.display = "block";
  } else {
    calendarpagebtn.style.display = "none";
  }
});
