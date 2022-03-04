#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <math.h>
#include <qpainter.h>
#include <QPainter>
#include <QMessageBox>
#include <iostream>
#include <QColor>
#include <QColorDialog>
#include "io.h"
#include "controller.h"
#include "rc.h"


MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    scene.scene = new QGraphicsScene(this);
    ui->graphicsView->setScene(scene.scene);
    ui->graphicsView->setBackgroundBrush(QBrush(Qt::white, Qt::SolidPattern));
    fig.arr = NULL;
    fig.matrix = NULL;
    fig.n = 0;
}

MainWindow::~MainWindow()
{
    delete [] fig.arr;
    free_matrix(fig.matrix, fig.n);
    delete ui;
}

void MainWindow::on_loadModelButton_clicked()
{
    dataAction.filename = "/Users/daria/Desktop/sem4/OOP/lab01/1.txt";
    ReturnCode rc = controller(scene, DOWNLOAD, dataAction);
    if (rc) print_warning(rc);
    rc = controller(scene, DRAW, dataAction);
    if (rc) print_warning(rc);
}

void MainWindow::on_scaleButton_clicked()
{
    double km = ui->scaleField->value();
    dataAction.scale.k = km;
    ReturnCode rc = controller(scene, SCALE, dataAction);
    if (rc) print_warning(rc);
    rc = controller(scene, DRAW, dataAction);
    if (rc) print_warning(rc);
}

void MainWindow::on_moveButton_clicked()
{
    double dx = ui->moveXField->value();
    double dy = ui->moveYField->value();
    double dz = ui->moveZField->value();
    dataAction.move.dx = dx;
    dataAction.move.dy = dy;
    dataAction.move.dz = dz;

    ReturnCode rc = controller(scene, MOVE, dataAction);
    if (rc) print_warning(rc);
    rc = controller(scene, DRAW, dataAction);
    if (rc) print_warning(rc);
}

void MainWindow::on_rotateButton_clicked()
{
    double ax = ui->rotateXField->value();
    double ay = ui->rotateYField->value();
    double az = ui->rotateZField->value();
    dataAction.rotation.ax = ax;
    dataAction.rotation.ay = ay;
    dataAction.rotation.az = az;

    ReturnCode rc =controller(scene, ROTATE, dataAction);
    if (rc) print_warning(rc);
    rc = controller(scene, DRAW, dataAction);
    if (rc) print_warning(rc);
}

void MainWindow::on_clearButton_clicked()
{
    ReturnCode rc =controller(scene, DELETE, dataAction);
    if (rc) print_warning(rc);
    rc = controller(scene, DRAW, dataAction);
    if (rc) print_warning(rc);
}

