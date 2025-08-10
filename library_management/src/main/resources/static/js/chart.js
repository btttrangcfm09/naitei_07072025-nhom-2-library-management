$(function() {
  /* ChartJS for Library Management System
   * -------
   * Data and config for library-specific charts
   */
  'use strict';

  // Books by Category Data
  var booksCategoryData = {
    labels: ["Fiction", "Science", "Technology", "History", "Biography", "Arts"],
    datasets: [{
      label: '# of Books',
      data: [45, 28, 35, 22, 18, 15],
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
      ],
      borderWidth: 1
    }]
  };

  // Monthly Borrowing Trends
  var monthlyBorrowingData = {
    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
    datasets: [{
        label: 'Books Borrowed',
        data: [65, 78, 90, 85, 92, 88, 95, 87, 98, 102, 89, 93],
        borderColor: ['#587ce4'],
        backgroundColor: 'rgba(88, 124, 228, 0.1)',
        borderWidth: 3,
        fill: true,
        tension: 0.4
      },
      {
        label: 'Books Returned',
        data: [60, 75, 85, 82, 88, 85, 90, 83, 95, 98, 85, 88],
        borderColor: ['#28a745'],
        backgroundColor: 'rgba(40, 167, 69, 0.1)',
        borderWidth: 3,
        fill: true,
        tension: 0.4
      },
      {
        label: 'Overdue Books',
        data: [5, 8, 12, 7, 9, 11, 8, 15, 6, 9, 12, 8],
        borderColor: ['#dc3545'],
        backgroundColor: 'rgba(220, 53, 69, 0.1)',
        borderWidth: 3,
        fill: true,
        tension: 0.4
      }
    ]
  };

  // User Activity Data
  var userActivityData = {
    labels: ["Active Members", "New Registrations", "Premium Members", "Student Members", "Faculty Members"],
    datasets: [{
      data: [120, 25, 45, 85, 35],
      backgroundColor: [
        'rgba(40, 167, 69, 0.8)',
        'rgba(54, 162, 235, 0.8)',
        'rgba(255, 193, 7, 0.8)',
        'rgba(220, 53, 69, 0.8)',
        'rgba(108, 117, 125, 0.8)'
      ],
      borderColor: [
        'rgba(40, 167, 69, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 193, 7, 1)',
        'rgba(220, 53, 69, 1)',
        'rgba(108, 117, 125, 1)'
      ],
      borderWidth: 2
    }],
    labels: [
      'Active Members',
      'New Registrations',
      'Premium Members',
      'Student Members',
      'Faculty Members'
    ]
  };

  // Most Popular Authors
  var popularAuthorsData = {
    labels: ["J.K. Rowling", "Stephen King", "Agatha Christie", "Dan Brown", "Paulo Coelho", "Harper Lee"],
    datasets: [{
      label: 'Books Borrowed',
      data: [45, 38, 42, 35, 28, 32],
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
      ],
      borderWidth: 1
    }]
  };

  var options = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'bottom'
      }
    },
    scales: {
      y: {
        beginAtZero: true
      }
    },
    elements: {
      point: {
        radius: 4
      }
    }
  };

  var doughnutPieOptions = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'bottom'
      }
    },
    animation: {
      animateScale: true,
      animateRotate: true
    }
  };

  var lineOptions = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'top'
      }
    },
    scales: {
      y: {
        beginAtZero: true,
        grid: {
          display: true
        }
      },
      x: {
        grid: {
          display: false
        }
      }
    },
    elements: {
      point: {
        radius: 4,
        hoverRadius: 6
      }
    }
  };

  // Library Dashboard Charts

  // Books by Category Chart
  if ($("#booksCategoryChart").length) {
    var categoryChartCanvas = $("#booksCategoryChart").get(0).getContext("2d");
    var categoryChart = new Chart(categoryChartCanvas, {
      type: 'doughnut',
      data: booksCategoryData,
      options: doughnutPieOptions
    });
  }

  // Monthly Borrowing Trends
  if ($("#monthlyBorrowingChart").length) {
    var borrowingChartCanvas = $("#monthlyBorrowingChart").get(0).getContext("2d");
    var borrowingChart = new Chart(borrowingChartCanvas, {
      type: 'line',
      data: monthlyBorrowingData,
      options: lineOptions
    });
  }

  // User Activity Chart
  if ($("#userActivityChart").length) {
    var userChartCanvas = $("#userActivityChart").get(0).getContext("2d");
    var userChart = new Chart(userChartCanvas, {
      type: 'pie',
      data: userActivityData,
      options: doughnutPieOptions
    });
  }

  // Popular Authors Bar Chart
  if ($("#popularAuthorsChart").length) {
    var authorsChartCanvas = $("#popularAuthorsChart").get(0).getContext("2d");
    var authorsChart = new Chart(authorsChartCanvas, {
      type: 'bar',
      data: popularAuthorsData,
      options: options
    });
  }

  // Library Statistics Overview (Area Chart)
  if ($("#libraryOverviewChart").length) {
    var overviewData = {
      labels: ["Week 1", "Week 2", "Week 3", "Week 4"],
      datasets: [{
          label: 'Books Issued',
          data: [65, 78, 85, 92],
          backgroundColor: 'rgba(54, 162, 235, 0.2)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 2,
          fill: true,
          tension: 0.4
        },
        {
          label: 'Books Returned',
          data: [58, 72, 80, 88],
          backgroundColor: 'rgba(40, 167, 69, 0.2)',
          borderColor: 'rgba(40, 167, 69, 1)',
          borderWidth: 2,
          fill: true,
          tension: 0.4
        },
        {
          label: 'New Members',
          data: [12, 15, 18, 22],
          backgroundColor: 'rgba(255, 193, 7, 0.2)',
          borderColor: 'rgba(255, 193, 7, 1)',
          borderWidth: 2,
          fill: true,
          tension: 0.4
        }
      ]
    };

    var overviewChartCanvas = $("#libraryOverviewChart").get(0).getContext("2d");
    var overviewChart = new Chart(overviewChartCanvas, {
      type: 'line',
      data: overviewData,
      options: lineOptions
    });
  }

  // Original charts with library context
  if ($("#barChart").length) {
    var barChartCanvas = $("#barChart").get(0).getContext("2d");
    var barChart = new Chart(barChartCanvas, {
      type: 'bar',
      data: booksCategoryData,
      options: options
    });
  }

  if ($("#lineChart").length) {
    var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
    var lineChart = new Chart(lineChartCanvas, {
      type: 'line',
      data: monthlyBorrowingData,
      options: lineOptions
    });
  }

  if ($("#doughnutChart").length) {
    var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
    var doughnutChart = new Chart(doughnutChartCanvas, {
      type: 'doughnut',
      data: userActivityData,
      options: doughnutPieOptions
    });
  }

  if ($("#pieChart").length) {
    var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
    var pieChart = new Chart(pieChartCanvas, {
      type: 'pie',
      data: booksCategoryData,
      options: doughnutPieOptions
    });
  }
});
