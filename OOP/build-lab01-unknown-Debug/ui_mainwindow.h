/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.12.12
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QDoubleSpinBox>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QGraphicsView>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralwidget;
    QGraphicsView *graphicsView;
    QWidget *gridLayoutWidget;
    QGridLayout *gridLayout_2;
    QFormLayout *formLayout_3;
    QDoubleSpinBox *scaleField;
    QLabel *label_7;
    QPushButton *scaleButton;
    QFormLayout *formLayout;
    QLabel *label;
    QDoubleSpinBox *moveXField;
    QLabel *label_2;
    QDoubleSpinBox *moveYField;
    QLabel *label_3;
    QDoubleSpinBox *moveZField;
    QPushButton *moveButton;
    QFormLayout *formLayout_2;
    QLabel *label_4;
    QDoubleSpinBox *rotateXField;
    QLabel *label_5;
    QDoubleSpinBox *rotateYField;
    QLabel *label_6;
    QDoubleSpinBox *rotateZField;
    QPushButton *rotateButton;
    QFormLayout *formLayout_5;
    QPushButton *clearButton;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QString::fromUtf8("MainWindow"));
        MainWindow->resize(1050, 770);
        centralwidget = new QWidget(MainWindow);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        graphicsView = new QGraphicsView(centralwidget);
        graphicsView->setObjectName(QString::fromUtf8("graphicsView"));
        graphicsView->setGeometry(QRect(20, 139, 1011, 581));
        gridLayoutWidget = new QWidget(centralwidget);
        gridLayoutWidget->setObjectName(QString::fromUtf8("gridLayoutWidget"));
        gridLayoutWidget->setGeometry(QRect(10, 10, 1021, 131));
        gridLayout_2 = new QGridLayout(gridLayoutWidget);
        gridLayout_2->setObjectName(QString::fromUtf8("gridLayout_2"));
        gridLayout_2->setContentsMargins(0, 0, 0, 0);
        formLayout_3 = new QFormLayout();
        formLayout_3->setObjectName(QString::fromUtf8("formLayout_3"));
        scaleField = new QDoubleSpinBox(gridLayoutWidget);
        scaleField->setObjectName(QString::fromUtf8("scaleField"));

        formLayout_3->setWidget(0, QFormLayout::FieldRole, scaleField);

        label_7 = new QLabel(gridLayoutWidget);
        label_7->setObjectName(QString::fromUtf8("label_7"));

        formLayout_3->setWidget(0, QFormLayout::LabelRole, label_7);

        scaleButton = new QPushButton(gridLayoutWidget);
        scaleButton->setObjectName(QString::fromUtf8("scaleButton"));

        formLayout_3->setWidget(1, QFormLayout::SpanningRole, scaleButton);


        gridLayout_2->addLayout(formLayout_3, 0, 3, 1, 1);

        formLayout = new QFormLayout();
        formLayout->setObjectName(QString::fromUtf8("formLayout"));
        label = new QLabel(gridLayoutWidget);
        label->setObjectName(QString::fromUtf8("label"));

        formLayout->setWidget(0, QFormLayout::LabelRole, label);

        moveXField = new QDoubleSpinBox(gridLayoutWidget);
        moveXField->setObjectName(QString::fromUtf8("moveXField"));

        formLayout->setWidget(0, QFormLayout::FieldRole, moveXField);

        label_2 = new QLabel(gridLayoutWidget);
        label_2->setObjectName(QString::fromUtf8("label_2"));

        formLayout->setWidget(1, QFormLayout::LabelRole, label_2);

        moveYField = new QDoubleSpinBox(gridLayoutWidget);
        moveYField->setObjectName(QString::fromUtf8("moveYField"));

        formLayout->setWidget(1, QFormLayout::FieldRole, moveYField);

        label_3 = new QLabel(gridLayoutWidget);
        label_3->setObjectName(QString::fromUtf8("label_3"));

        formLayout->setWidget(2, QFormLayout::LabelRole, label_3);

        moveZField = new QDoubleSpinBox(gridLayoutWidget);
        moveZField->setObjectName(QString::fromUtf8("moveZField"));

        formLayout->setWidget(2, QFormLayout::FieldRole, moveZField);

        moveButton = new QPushButton(gridLayoutWidget);
        moveButton->setObjectName(QString::fromUtf8("moveButton"));

        formLayout->setWidget(3, QFormLayout::SpanningRole, moveButton);


        gridLayout_2->addLayout(formLayout, 0, 1, 1, 1);

        formLayout_2 = new QFormLayout();
        formLayout_2->setObjectName(QString::fromUtf8("formLayout_2"));
        label_4 = new QLabel(gridLayoutWidget);
        label_4->setObjectName(QString::fromUtf8("label_4"));

        formLayout_2->setWidget(0, QFormLayout::LabelRole, label_4);

        rotateXField = new QDoubleSpinBox(gridLayoutWidget);
        rotateXField->setObjectName(QString::fromUtf8("rotateXField"));

        formLayout_2->setWidget(0, QFormLayout::FieldRole, rotateXField);

        label_5 = new QLabel(gridLayoutWidget);
        label_5->setObjectName(QString::fromUtf8("label_5"));

        formLayout_2->setWidget(1, QFormLayout::LabelRole, label_5);

        rotateYField = new QDoubleSpinBox(gridLayoutWidget);
        rotateYField->setObjectName(QString::fromUtf8("rotateYField"));

        formLayout_2->setWidget(1, QFormLayout::FieldRole, rotateYField);

        label_6 = new QLabel(gridLayoutWidget);
        label_6->setObjectName(QString::fromUtf8("label_6"));

        formLayout_2->setWidget(2, QFormLayout::LabelRole, label_6);

        rotateZField = new QDoubleSpinBox(gridLayoutWidget);
        rotateZField->setObjectName(QString::fromUtf8("rotateZField"));

        formLayout_2->setWidget(2, QFormLayout::FieldRole, rotateZField);

        rotateButton = new QPushButton(gridLayoutWidget);
        rotateButton->setObjectName(QString::fromUtf8("rotateButton"));

        formLayout_2->setWidget(3, QFormLayout::SpanningRole, rotateButton);


        gridLayout_2->addLayout(formLayout_2, 0, 2, 1, 1);

        formLayout_5 = new QFormLayout();
        formLayout_5->setObjectName(QString::fromUtf8("formLayout_5"));
        clearButton = new QPushButton(gridLayoutWidget);
        clearButton->setObjectName(QString::fromUtf8("clearButton"));

        formLayout_5->setWidget(0, QFormLayout::FieldRole, clearButton);


        gridLayout_2->addLayout(formLayout_5, 0, 0, 1, 1);

        MainWindow->setCentralWidget(centralwidget);
        menubar = new QMenuBar(MainWindow);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        menubar->setGeometry(QRect(0, 0, 1050, 22));
        MainWindow->setMenuBar(menubar);
        statusbar = new QStatusBar(MainWindow);
        statusbar->setObjectName(QString::fromUtf8("statusbar"));
        MainWindow->setStatusBar(statusbar);

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QApplication::translate("MainWindow", "MainWindow", nullptr));
        label_7->setText(QApplication::translate("MainWindow", "kx:", nullptr));
        scaleButton->setText(QApplication::translate("MainWindow", "\320\234\320\260\321\201\321\210\321\202\320\260\320\261\320\270\321\200\320\276\320\262\320\260\321\202\321\214", nullptr));
        label->setText(QApplication::translate("MainWindow", "dx:", nullptr));
        label_2->setText(QApplication::translate("MainWindow", "dy:", nullptr));
        label_3->setText(QApplication::translate("MainWindow", "dz:", nullptr));
        moveButton->setText(QApplication::translate("MainWindow", "\320\237\320\265\321\200\320\265\320\274\320\265\321\201\321\202\320\270\321\202\321\214", nullptr));
        label_4->setText(QApplication::translate("MainWindow", "x:", nullptr));
        label_5->setText(QApplication::translate("MainWindow", "y:", nullptr));
        label_6->setText(QApplication::translate("MainWindow", "z:", nullptr));
        rotateButton->setText(QApplication::translate("MainWindow", "\320\237\320\276\320\262\320\265\321\200\320\275\321\203\321\202\321\214", nullptr));
        clearButton->setText(QApplication::translate("MainWindow", "\320\241\320\261\321\200\320\276\321\201\320\270\321\202\321\214 \320\277\321\200\320\265\320\276\320\261\321\200\320\260\320\267\320\276\320\262\320\260\320\275\320\270\321\217", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
