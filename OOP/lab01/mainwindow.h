#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QPainter>
#include <QtCore>
#include <QtGui>
#include <QGraphicsScene>
#include "io.h"
#include "points.h"
#include "myscene.h"
#include "figure.h"
#include "action.h"

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
    void on_loadModelButton_clicked();

    void on_scaleButton_clicked();

    void on_moveButton_clicked();

    void on_rotateButton_clicked();

    void on_clearButton_clicked();

private:
    Ui::MainWindow *ui;
    myscene_t scene;
    struct figure fig;
    struct data dataAction;
};

#endif // MAINWINDOW_H

