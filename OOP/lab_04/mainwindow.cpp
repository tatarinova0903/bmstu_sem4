#include <QVBoxLayout>

#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow()
{
    this->lift = std::make_shared<Lift>(FLOORS_COUNT);
    int i = 0;
    for (auto &button: this->lift->getButtons())
    {
        this->uiButtons.push_back(std::make_shared<QPushButton>(QString::fromStdString(std::to_string(i + 1))));
        auto btn_ptr = button.get();
        auto ui_btn_ptr = this->uiButtons[i].get();
        connect(ui_btn_ptr, &QPushButton::clicked, btn_ptr, &MyButton::slotActive);
        i++;
    }
    this->centralWidget = std::make_shared<QWidget>(this);
    this->centralWidget->setFixedWidth(300);
    this->centralWidget->setFixedHeight(400);
    this->layout = std::make_shared<QGridLayout>(nullptr);
    this->titleLabel = std::make_shared<QLabel>("<h1>Управление лифтом</h1>");
    this->layout->addWidget(this->titleLabel.get(), 0, 0, 1, 1);
    i = 1;
    for (auto ui_btn_it = this->uiButtons.rbegin(); ui_btn_it != this->uiButtons.rend(); ++ui_btn_it)
    {
        this->layout->addWidget((*ui_btn_it).get(), i++, 0, 1, 1);
    }
    this->centralWidget->setLayout(this->layout.get());
    this->setCentralWidget(this->centralWidget.get());
}


