#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "lift.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private slots:
    void on_but_cabin_1_clicked();

    void on_but_cabin_2_clicked();

    void on_but_cabin_3_clicked();

    void on_but_cabin_4_clicked();

    void on_but_cabin_5_clicked();

    void on_but_cabin_6_clicked();

    void on_but_cabin_7_clicked();

    void on_but_cabin_8_clicked();

    void on_but_cabin_9_clicked();

    void on_but_cabin_10_clicked();

    void on_but_floor_1_clicked();

    void on_but_floor_2_clicked();

    void on_but_floor_3_clicked();

    void on_but_floor_4_clicked();

    void on_but_floor_5_clicked();

    void on_but_floor_6_clicked();

    void on_but_floor_7_clicked();

    void on_but_floor_8_clicked();

    void on_but_floor_9_clicked();

    void on_but_floor_10_clicked();

private:
    Ui::MainWindow *ui;
    Lift lift;
};

#endif // MAINWINDOW_H

