#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QVBoxLayout>
#include <QMainWindow>
#include <QLabel>
#include "lift.hpp"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
public:
    MainWindow();

private:
    std::shared_ptr<Lift> lift;
    std::shared_ptr<QWidget> centralWidget;
    std::shared_ptr<QGridLayout> layout;
    std::shared_ptr<QLabel> titleLabel;
    std::vector<std::shared_ptr<QPushButton>> uiButtons;
};

#endif // MAINWINDOW_H

