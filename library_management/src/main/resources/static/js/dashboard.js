(function ($) {
  'use strict';
  $(function () {
    function updateWelcomeInfo() {
          const welcomeEl = document.getElementById('welcome-message');
          const datetimeEl = document.getElementById('current-datetime');

          if (welcomeEl) {
            const profileName = document.querySelector('.profile-text')?.textContent || 'Xin chào, Admin!';
            welcomeEl.textContent = profileName.trim();
          }

          if (datetimeEl) {
            const now = new Date();
            const days = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
            const dayName = days[now.getDay()];
            const date = now.getDate();
            const month = now.getMonth() + 1;
            const year = now.getFullYear();

            let hours = now.getHours();
            const minutes = now.getMinutes().toString().padStart(2, '0');
            const ampm = hours >= 12 ? 'PM' : 'AM';
            hours = hours % 12;
            hours = hours ? hours : 12; // giờ '0' sẽ là '12'

            const formattedTime = `${hours}:${minutes} ${ampm}`;
            const formattedDate = `Ngày ${date} tháng ${month}, ${year}`;

            datetimeEl.textContent = `${formattedDate} | ${dayName}, ${formattedTime}`;
          }
        }

        updateWelcomeInfo();
        setInterval(updateWelcomeInfo, 60000); // 60000ms = 1 phút
    if ($('#dashboard-area-chart').length) {
      var lineChartCanvas = $("#dashboard-area-chart").get(0).getContext("2d");
      var data = {
        labels: ["T2", "T3", "T4", "T5", "T6", "T7", "CN"],
        datasets: [{
            label: 'Lượt thăm thư viện',
            data: [120, 190, 150, 200, 180, 250, 170],
            backgroundColor: 'rgba(220, 53, 69, 0.3)',
            borderColor: 'rgba(220, 53, 69, 1)',
            borderWidth: 2,
            fill: 'origin',
            tension: 0.4
          },
          {
            label: 'Lượt mượn sách',
            data: [80, 120, 100, 140, 110, 160, 130],
            backgroundColor: 'rgba(40, 167, 69, 0.3)',
            borderColor: 'rgba(40, 167, 69, 1)',
            borderWidth: 2,
            fill: 'origin',
            tension: 0.4
          }
        ]
      };
      var options = {
        responsive: true,
        maintainAspectRatio: true,
        animation: false,
        plugins: {
          legend: {
            display: false
          },
          tooltip: {
            backgroundColor: 'rgba(0, 0, 0, 0.8)',
            titleColor: '#fff',
            bodyColor: '#fff',
            borderColor: 'rgba(255, 255, 255, 0.2)',
            borderWidth: 1,
            callbacks: {
              label: function(context) {
                let label = context.dataset.label || '';
                if (label) {
                  label += ': ';
                }
                label += context.parsed.y + ' lượt';
                return label;
              }
            }
          }
        },
        scales: {
          y: {
            display: true,
            beginAtZero: true,
            grid: {
              color: 'rgba(0, 0, 0, 0.1)',
              drawBorder: false
            },
            ticks: {
              color: '#666',
              font: {
                size: 12
              },
              callback: function(value) {
                return value + ' lượt';
              }
            }
          },
          x: {
            display: true,
            grid: {
              display: false
            },
            ticks: {
              color: '#666',
              font: {
                size: 12
              }
            }
          }
        },
        elements: {
          point: {
            radius: 4,
            hoverRadius: 6,
            backgroundColor: '#fff',
            borderWidth: 2
          },
          line: {
            tension: 0.4
          }
        },
        layout: {
          padding: {
            left: 0,
            right: 0,
            top: 10,
            bottom: 0
          }
        },
        interaction: {
          intersect: false,
          mode: 'index'
        }
      };

      var lineChart = new Chart(lineChartCanvas, {
        type: 'line',
        data: data,
        options: options
      });

      // Animate chart on load
      lineChart.update('active');

      // Update chart data periodically (demo purpose)
      setInterval(function() {
        var newVisitors = Math.floor(Math.random() * 50) + 150;
        var newBorrowers = Math.floor(Math.random() * 40) + 100;

        // Only update if chart is visible and page is active
        if (document.visibilityState === 'visible') {
          // Shift data arrays
          data.datasets[0].data.shift();
          data.datasets[0].data.push(newVisitors);
          data.datasets[1].data.shift();
          data.datasets[1].data.push(newBorrowers);

          lineChart.update('none');
        }
      }, 30000); // Update every 30 seconds

      // Chart animation on hover
      $('#dashboard-area-chart').hover(
        function() {
          $(this).css('transform', 'scale(1.02)');
          $(this).css('transition', 'transform 0.2s ease');
        },
        function() {
          $(this).css('transform', 'scale(1)');
        }
      );
    }

    // Statistics cards animation
    $('.card-statistics').each(function(index) {
      $(this).css('animation-delay', (index * 0.1) + 's');
      $(this).addClass('fadeInUp');
    });

    // Book items hover effect
    $('.book-item').hover(
      function() {
        $(this).find('.book-cover').css('transform', 'scale(1.05)');
        $(this).find('.book-cover').css('transition', 'transform 0.3s ease');
        $(this).find('.book-cover').css('box-shadow', '0 8px 25px rgba(0,0,0,0.15)');
      },
      function() {
        $(this).find('.book-cover').css('transform', 'scale(1)');
        $(this).find('.book-cover').css('box-shadow', '0 2px 10px rgba(0,0,0,0.1)');
      }
    );

    // Table row hover effect
    $('table tbody tr').hover(
      function() {
        $(this).css('background-color', 'rgba(0, 123, 255, 0.05)');
        $(this).css('transition', 'background-color 0.2s ease');
      },
      function() {
        $(this).css('background-color', 'transparent');
      }
    );

    // Badge animation on load
    $('.badge').each(function(index) {
      setTimeout(() => {
        $(this).addClass('pulse-animation');
      }, index * 100);
    });

    // Notification counter animation
    $('.count').addClass('bounce-in');

    // Auto-refresh notifications (demo)
    let notificationCount = 5;
    setInterval(function() {
      if (Math.random() > 0.7) { // 30% chance
        notificationCount++;
        $('.count').text(notificationCount);
        $('.count').addClass('bounce-in');
        setTimeout(() => {
          $('.count').removeClass('bounce-in');
        }, 600);
      }
    }, 60000); // Check every minute
  });
})(jQuery);

// Additional CSS animations (add to your CSS file)
const additionalCSS = `
<style>
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translate3d(0, 40px, 0);
  }
  to {
    opacity: 1;
    transform: translate3d(0, 0, 0);
  }
}

@keyframes pulse-animation {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}

@keyframes bounce-in {
  0% {
    transform: scale(0.3);
    opacity: 0;
  }
  50% {
    transform: scale(1.05);
  }
  70% {
    transform: scale(0.9);
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

.fadeInUp {
  animation: fadeInUp 0.6s ease-out forwards;
}

.pulse-animation {
  animation: pulse-animation 1s ease-in-out;
}

.bounce-in {
  animation: bounce-in 0.6s ease-out;
}

.book-cover img {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.card-statistics {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.card-statistics:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.15);
}

.table tbody tr {
  transition: background-color 0.2s ease;
}

.legend-indicator {
  animation: pulse-animation 2s infinite;
}
</style>
`;

// Inject additional CSS
document.head.insertAdjacentHTML('beforeend', additionalCSS);
