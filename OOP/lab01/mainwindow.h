#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QPainter>
#include <QtCore>
#include <QtGui>
#include <QGraphicsScene>
#include "input.h"
#include "points.h"
#include "myscene.h"
#include "figure.h"
#include "action.h"

#define FILE_NAME "/Users/daria/Desktop/sem4/OOP/lab01/1.txt"

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
    void on_scaleButton_clicked();

    void on_moveButton_clicked();

    void on_rotateButton_clicked();

    void on_clearButton_clicked();

private:
    Ui::MainWindow *ui;
    myscene_t scene;
    figure_t fig;
    data_t data_action;
};

#endif // MAINWINDOW_H

