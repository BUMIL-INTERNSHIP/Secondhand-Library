document.addEventListener("DOMContentLoaded", () => {
    // Function to handle page navigation
    const handlePageNavigation = (event) => {
        const target = event.target;
        const currentPage = target.closest(".page");
        const isNextButton = target.classList.contains("next");
        const isPrevButton = target.classList.contains("prev");

        let nextPage = null;

        // Determine next or previous page
        if (isNextButton) {
            nextPage = target
                .closest(".form-outer")
                .querySelector(
                    `.page:nth-of-type(${
                        Array.from(currentPage.parentNode.children).indexOf(currentPage) + 2
                    })`
                );
        } else if (isPrevButton) {
            nextPage = target
                .closest(".form-outer")
                .querySelector(
                    `.page:nth-of-type(${Array.from(
                        currentPage.parentNode.children
                    ).indexOf(currentPage)})`
                );
        }

        if (isNextButton) {
            const selectedItem = currentPage.querySelector(".grid-item.selected");
            if (selectedItem) {
                const hiddenInput = currentPage.querySelector('input[type="hidden"]');
                if (hiddenInput) {
                    const currentValue = hiddenInput.value;
                    const newValue = selectedItem.getAttribute("data-value");
                    hiddenInput.value = currentValue ? `${currentValue},${newValue}` : newValue;
                }
            }
        }

        // Switch pages
        if (nextPage) {
            currentPage.classList.add("slide-page");
            nextPage.classList.remove("slide-page");
            updateProgressBar();
        }
    };

    // Update progress bar based on current page
    const updateProgressBar = () => {
        const pages = document.querySelectorAll(".form-outer .page");
        const progressSteps = document.querySelectorAll(".progress-bar .step");
        const currentIndex = Array.from(pages).findIndex(
            (page) => !page.classList.contains("slide-page")
        );

        progressSteps.forEach((step, index) => {
            step.classList.toggle("active", index < currentIndex);
            step
                .querySelector(".check")
                .classList.toggle("active", index < currentIndex);
        });
    };

    // Function to handle form submission
    const handleSubmit = () => {
        const inputs = document.querySelectorAll('input[type="hidden"]');
        const values = Array.from(inputs).reduce((acc, input) => {
            acc[input.name] = input.value;
            return acc;
        }, {});

        console.log("Form Data:", values);
        // 실제 폼 제출
        document.querySelector("form").submit();
    };

    // Add event listeners to navigation buttons
    document
        .querySelector(".form-outer")
        .addEventListener("click", handlePageNavigation);

    // Add event listener to grid items to handle selection
    const gridItems = document.querySelectorAll(".grid-item");
    gridItems.forEach((item) => {
        item.addEventListener("click", () => {
            // Remove 'selected' class from all items and add to the clicked item
            item.parentNode
                .querySelectorAll(".grid-item.selected")
                .forEach((i) => i.classList.remove("selected"));
            item.classList.add("selected");
        });
    });

    // Add event listener to submit button
    document.querySelector(".submit").addEventListener("click", (event) => {
        event.preventDefault(); // Prevent actual form submission
        // Update the hidden input on the last page before submitting
        const finalPage = document.querySelector(".page:last-child");
        const selectedItem = finalPage.querySelector(".grid-item.selected");
        if (selectedItem) {
            const hiddenInput = finalPage.querySelector('input[type="hidden"]');
            if (hiddenInput) {
                const currentValue = hiddenInput.value;
                const newValue = selectedItem.getAttribute("data-value");
                hiddenInput.value = currentValue ? `${currentValue},${newValue}` : newValue;
            }
        }
        handleSubmit();
    });
});
