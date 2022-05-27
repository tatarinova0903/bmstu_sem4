#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    lift.set_text_edit(ui->textEdit);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_but_cabin_1_clicked()
{
    lift.set_floor(1);
}

void MainWindow::on_but_cabin_2_clicked()
{
    lift.set_floor(2);
}

void MainWindow::on_but_cabin_3_clicked()
{
    lift.set_floor(3);
}

void MainWindow::on_but_cabin_4_clicked()
{
    lift.set_floor(4);
}

void MainWindow::on_but_cabin_5_clicked()
{
    lift.set_floor(5);
}

void MainWindow::on_but_cabin_6_clicked()
{
    lift.set_floor(6);
}

void MainWindow::on_but_cabin_7_clicked()
{
    lift.set_floor(7);
}

void MainWindow::on_but_cabin_8_clicked()
{
    lift.set_floor(8);
}

void MainWindow::on_but_cabin_9_clicked()
{
    lift.set_floor(9);
}

void MainWindow::on_but_cabin_10_clicked()
{
    lift.set_floor(10);
}

void MainWindow::on_but_floor_1_clicked()
{
    lift.set_floor(1);
}

void MainWindow::on_but_floor_2_clicked()
{
    lift.set_floor(2);
}

void MainWindow::on_but_floor_3_clicked()
{
    lift.set_floor(3);
}

void MainWindow::on_but_floor_4_clicked()
{
    lift.set_floor(4);
}

void MainWindow::on_but_floor_5_clicked()
{
    lift.set_floor(5);
}

void MainWindow::on_but_floor_6_clicked()
{
    lift.set_floor(6);
}

void MainWindow::on_but_floor_7_clicked()
{
    lift.set_floor(7);
}

void MainWindow::on_but_floor_8_clicked()
{
    lift.set_floor(8);
}

void MainWindow::on_but_floor_9_clicked()
{
    lift.set_floor(9);
}

void MainWindow::on_but_floor_10_clicked()
{
    lift.set_floor(10);
}

