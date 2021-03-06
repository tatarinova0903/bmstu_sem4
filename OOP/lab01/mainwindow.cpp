#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <math.h>
#include <qpainter.h>
#include <QPainter>
#include <QMessageBox>
#include <iostream>
#include <QColor>
#include <QColorDialog>
#include "input.h"
#include "controller.h"
#include "return_code.h"


MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    scene.scene = new QGraphicsScene(this);
    ui->graphicsView->setScene(scene.scene);
    ui->graphicsView->setBackgroundBrush(QBrush(Qt::white, Qt::SolidPattern));
    fig = init_fig();
    data_action.filename = FILE_NAME;
    return_code rc = controller(scene, DOWNLOAD, data_action);
    if (rc)
    {
        print_warning(rc);
    }
    rc = controller(scene, DRAW, data_action);
    if (rc)
    {
        print_warning(rc);
    }
}

MainWindow::~MainWindow()
{
    delete [] fig.arr;
    free_matrix(fig.matrix, get_fig_n(fig));
    delete ui;
}

void MainWindow::on_scaleButton_clicked()
{
    double km = ui->scaleField->value();
    data_action.scale.k = km;
    return_code rc = controller(scene, SCALE, data_action);
    if (rc)
    {
        print_warning(rc);
    }
    rc = controller(scene, DRAW, data_action);
    if (rc)
    {
        print_warning(rc);
    }
}

void MainWindow::on_moveButton_clicked()
{
    double dx = ui->moveXField->value();
    double dy = ui->moveYField->value();
    double dz = ui->moveZField->value();
    data_action.move.dx = dx;
    data_action.move.dy = dy;
    data_action.move.dz = dz;

    return_code rc = controller(scene, MOVE, data_action);
    if (rc)
    {
        print_warning(rc);
    }
    rc = controller(scene, DRAW, data_action);
    if (rc)
    {
        print_warning(rc);
    }
}

void MainWindow::on_rotateButton_clicked()
{
    double ax = ui->rotateXField->value();
    double ay = ui->rotateYField->value();
    double az = ui->rotateZField->value();
    data_action.rotation.ax = ax;
    data_action.rotation.ay = ay;
    data_action.rotation.az = az;

    return_code rc = controller(scene, ROTATE, data_action);
    if (rc)
    {
        print_warning(rc);
    }
    rc = controller(scene, DRAW, data_action);
    if (rc)
    {
        print_warning(rc);
    }
}

void MainWindow::on_clearButton_clicked()
{
    return_code rc = controller(scene, DELETE, data_action);
    if (rc) print_warning(rc);
    data_action.filename = FILE_NAME;
    rc = controller(scene, DOWNLOAD, data_action);
    if (rc)
    {
        print_warning(rc);
    }
    rc = controller(scene, DRAW, data_action);
    if (rc)
    {
        print_warning(rc);
    }
}

