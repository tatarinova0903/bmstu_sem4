#ifndef LDOORS_H
#define LDOORS_H

#include <QObject>
#include <QTimer>

#include "common.h"

class ldoors: public QObject
{
    Q_OBJECT
public:
    explicit ldoors(QObject *parent = 0);
    enum DoorsState
    {
      CLOSE,
      OPEN,
      IN_CLOSE,
      IN_OPEN
    };
    void set_text_edit(QTextEdit *t);

public slots:

    void start_closing();
    void start_opening();

private slots:

    void open();
    void close();

signals:
    void closed_doors();
    void open_doors();

private:
    DoorsState state;
    QTimer openingTimer;
    QTimer closingTimer;
    QTimer stayopenTimer;

    QTextEdit *text;
};

#endif // LDOORS_H

