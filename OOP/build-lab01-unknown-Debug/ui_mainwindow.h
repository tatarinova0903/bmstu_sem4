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
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralwidget;
    QGraphicsView *graphicsView;
    QWidget *verticalLayoutWidget;
    QVBoxLayout *verticalLayout;
    QHBoxLayout *horizontalLayout;
    QPushButton *loadModelButton;
    QSpacerItem *verticalSpacer_3;
    QLabel *label_10;
    QFormLayout *formLayout_3;
    QLabel *label_6;
    QDoubleSpinBox *scaleField;
    QPushButton *scaleButton;
    QSpacerItem *verticalSpacer_2;
    QLabel *label_9;
    QFormLayout *formLayout_2;
    QLabel *label_2;
    QDoubleSpinBox *moveXField;
    QLabel *label_5;
    QDoubleSpinBox *moveYField;
    QDoubleSpinBox *moveZField;
    QLabel *label_12;
    QPushButton *moveButton;
    QSpacerItem *verticalSpacer;
    QLabel *label_8;
    QFormLayout *formLayout;
    QLabel *label;
    QDoubleSpinBox *rotateXField;
    QDoubleSpinBox *rotateYField;
    QLabel *label_3;
    QLabel *label_4;
    QDoubleSpinBox *rotateZField;
    QPushButton *rotateButton;
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
        graphicsView->setGeometry(QRect(20, 20, 700, 700));
        verticalLayoutWidget = new QWidget(centralwidget);
        verticalLayoutWidget->setObjectName(QString::fromUtf8("verticalLayoutWidget"));
        verticalLayoutWidget->setGeometry(QRect(730, 20, 301, 701));
        verticalLayout = new QVBoxLayout(verticalLayoutWidget);
        verticalLayout->setObjectName(QString::fromUtf8("verticalLayout"));
        verticalLayout->setContentsMargins(0, 0, 0, 0);
        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName(QString::fromUtf8("horizontalLayout"));

        verticalLayout->addLayout(horizontalLayout);

        loadModelButton = new QPushButton(verticalLayoutWidget);
        loadModelButton->setObjectName(QString::fromUtf8("loadModelButton"));

        verticalLayout->addWidget(loadModelButton);

        verticalSpacer_3 = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer_3);

        label_10 = new QLabel(verticalLayoutWidget);
        label_10->setObjectName(QString::fromUtf8("label_10"));

        verticalLayout->addWidget(label_10);

        formLayout_3 = new QFormLayout();
        formLayout_3->setObjectName(QString::fromUtf8("formLayout_3"));
        formLayout_3->setHorizontalSpacing(1);
        formLayout_3->setVerticalSpacing(2);
        label_6 = new QLabel(verticalLayoutWidget);
        label_6->setObjectName(QString::fromUtf8("label_6"));

        formLayout_3->setWidget(2, QFormLayout::LabelRole, label_6);

        scaleField = new QDoubleSpinBox(verticalLayoutWidget);
        scaleField->setObjectName(QString::fromUtf8("scaleField"));
        scaleField->setMinimum(-1000.000000000000000);
        scaleField->setMaximum(1000.000000000000000);

        formLayout_3->setWidget(2, QFormLayout::FieldRole, scaleField);


        verticalLayout->addLayout(formLayout_3);

        scaleButton = new QPushButton(verticalLayoutWidget);
        scaleButton->setObjectName(QString::fromUtf8("scaleButton"));

        verticalLayout->addWidget(scaleButton);

        verticalSpacer_2 = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer_2);

        label_9 = new QLabel(verticalLayoutWidget);
        label_9->setObjectName(QString::fromUtf8("label_9"));

        verticalLayout->addWidget(label_9);

        formLayout_2 = new QFormLayout();
        formLayout_2->setObjectName(QString::fromUtf8("formLayout_2"));
        label_2 = new QLabel(verticalLayoutWidget);
        label_2->setObjectName(QString::fromUtf8("label_2"));

        formLayout_2->setWidget(2, QFormLayout::LabelRole, label_2);

        moveXField = new QDoubleSpinBox(verticalLayoutWidget);
        moveXField->setObjectName(QString::fromUtf8("moveXField"));
        moveXField->setMinimum(-1000.000000000000000);
        moveXField->setMaximum(1000.000000000000000);

        formLayout_2->setWidget(2, QFormLayout::FieldRole, moveXField);

        label_5 = new QLabel(verticalLayoutWidget);
        label_5->setObjectName(QString::fromUtf8("label_5"));

        formLayout_2->setWidget(4, QFormLayout::LabelRole, label_5);

        moveYField = new QDoubleSpinBox(verticalLayoutWidget);
        moveYField->setObjectName(QString::fromUtf8("moveYField"));
        moveYField->setMinimum(-1000.000000000000000);
        moveYField->setMaximum(1000.000000000000000);

        formLayout_2->setWidget(4, QFormLayout::FieldRole, moveYField);

        moveZField = new QDoubleSpinBox(verticalLayoutWidget);
        moveZField->setObjectName(QString::fromUtf8("moveZField"));
        moveZField->setMinimum(-1000.000000000000000);
        moveZField->setMaximum(1000.000000000000000);

        formLayout_2->setWidget(5, QFormLayout::FieldRole, moveZField);

        label_12 = new QLabel(verticalLayoutWidget);
        label_12->setObjectName(QString::fromUtf8("label_12"));

        formLayout_2->setWidget(5, QFormLayout::LabelRole, label_12);


        verticalLayout->addLayout(formLayout_2);

        moveButton = new QPushButton(verticalLayoutWidget);
        moveButton->setObjectName(QString::fromUtf8("moveButton"));

        verticalLayout->addWidget(moveButton);

        verticalSpacer = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout->addItem(verticalSpacer);

        label_8 = new QLabel(verticalLayoutWidget);
        label_8->setObjectName(QString::fromUtf8("label_8"));

        verticalLayout->addWidget(label_8);

        formLayout = new QFormLayout();
        formLayout->setObjectName(QString::fromUtf8("formLayout"));
        label = new QLabel(verticalLayoutWidget);
        label->setObjectName(QString::fromUtf8("label"));

        formLayout->setWidget(0, QFormLayout::LabelRole, label);

        rotateXField = new QDoubleSpinBox(verticalLayoutWidget);
        rotateXField->setObjectName(QString::fromUtf8("rotateXField"));
        rotateXField->setMinimum(-1000.000000000000000);
        rotateXField->setMaximum(1000.000000000000000);

        formLayout->setWidget(0, QFormLayout::FieldRole, rotateXField);

        rotateYField = new QDoubleSpinBox(verticalLayoutWidget);
        rotateYField->setObjectName(QString::fromUtf8("rotateYField"));
        rotateYField->setMinimum(-1000.000000000000000);
        rotateYField->setMaximum(1000.000000000000000);

        formLayout->setWidget(2, QFormLayout::FieldRole, rotateYField);

        label_3 = new QLabel(verticalLayoutWidget);
        label_3->setObjectName(QString::fromUtf8("label_3"));

        formLayout->setWidget(2, QFormLayout::LabelRole, label_3);

        label_4 = new QLabel(verticalLayoutWidget);
        label_4->setObjectName(QString::fromUtf8("label_4"));

        formLayout->setWidget(3, QFormLayout::LabelRole, label_4);

        rotateZField = new QDoubleSpinBox(verticalLayoutWidget);
        rotateZField->setObjectName(QString::fromUtf8("rotateZField"));
        rotateZField->setMinimum(-1000.000000000000000);
        rotateZField->setMaximum(1000.000000000000000);

        formLayout->setWidget(3, QFormLayout::FieldRole, rotateZField);


        verticalLayout->addLayout(formLayout);

        rotateButton = new QPushButton(verticalLayoutWidget);
        rotateButton->setObjectName(QString::fromUtf8("rotateButton"));

        verticalLayout->addWidget(rotateButton);

        clearButton = new QPushButton(verticalLayoutWidget);
        clearButton->setObjectName(QString::fromUtf8("clearButton"));

        verticalLayout->addWidget(clearButton);

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
        loadModelButton->setText(QApplication::translate("MainWindow", "\320\227\320\260\320\263\321\200\321\203\320\267\320\270\321\202\321\214 \320\274\320\276\320\264\320\265\320\273\321\214", nullptr));
        label_10->setText(QApplication::translate("MainWindow", "\320\222\320\262\320\265\320\264\320\270\321\202\320\265 \320\272\320\276\321\215\321\204\320\270\321\206\320\265\320\275\321\202 \320\274\320\260\321\201\321\210\321\202\320\260\320\261\320\270\321\200\320\276\320\262\320\260\320\275\320\270\321\217:", nullptr));
        label_6->setText(QApplication::translate("MainWindow", "K: ", nullptr));
        scaleButton->setText(QApplication::translate("MainWindow", "\320\234\320\260\321\201\321\210\321\202\320\260\320\261\320\270\321\200\320\276\320\262\320\260\321\202\321\214", nullptr));
        label_9->setText(QApplication::translate("MainWindow", "\320\222\320\262\320\265\320\264\320\270\321\202\320\265 \320\272\320\276\321\215\321\204\320\270\321\206\321\206\320\265\320\275\321\202\321\213 \320\277\320\265\321\200\320\265\320\275\320\276\321\201\320\260:", nullptr));
        label_2->setText(QApplication::translate("MainWindow", "dx:", nullptr));
        label_5->setText(QApplication::translate("MainWindow", "dy:", nullptr));
        label_12->setText(QApplication::translate("MainWindow", "dz:", nullptr));
        moveButton->setText(QApplication::translate("MainWindow", "\320\237\320\265\321\200\320\265\320\275\320\265\321\201\321\202\320\270", nullptr));
        label_8->setText(QApplication::translate("MainWindow", "\320\222\320\262\320\265\320\264\320\270\321\202\320\265 \321\203\320\263\320\273\321\213 \320\264\320\273\321\217 \320\277\320\276\320\262\320\276\321\200\320\276\321\202\320\260", nullptr));
        label->setText(QApplication::translate("MainWindow", "alphax", nullptr));
        label_3->setText(QApplication::translate("MainWindow", "alphay", nullptr));
        label_4->setText(QApplication::translate("MainWindow", "alphaz", nullptr));
        rotateButton->setText(QApplication::translate("MainWindow", "\320\237\320\276\320\262\320\265\321\200\320\275\321\203\321\202\321\214", nullptr));
        clearButton->setText(QApplication::translate("MainWindow", "\320\241\320\261\321\200\320\276\321\201\320\270\321\202\321\214 \320\274\320\276\320\264\320\265\320\273\321\214", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
