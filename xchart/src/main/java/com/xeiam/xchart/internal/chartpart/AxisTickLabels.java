/**
 * Copyright 2011 - 2013 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xeiam.xchart.internal.chartpart;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

/**
 * Axis tick labels
 */
public class AxisTickLabels implements ChartPart {

  /** parent */
  private final AxisTick axisTick;

  /** the bounds */
  private Rectangle2D bounds = new Rectangle2D.Double();

  /**
   * Constructor
   * 
   * @param axisTick
   */
  protected AxisTickLabels(AxisTick axisTick) {

    this.axisTick = axisTick;
  }

  @Override
  public Rectangle2D getBounds() {

    return bounds;
  }

  @Override
  public void paint(Graphics2D g) {

    g.setFont(getChartPainter().getStyleManager().getAxisTickLabelsFont());

    g.setColor(getChartPainter().getStyleManager().getAxisTickLabelsColor());

    if (axisTick.getAxis().getDirection() == Axis.Direction.Y && getChartPainter().getStyleManager().isYAxisTicksVisible()) { // Y-Axis

      double xOffset = axisTick.getAxis().getAxisTitle().getBounds().getX() + axisTick.getAxis().getAxisTitle().getBounds().getWidth();
      double yOffset = axisTick.getAxis().getPaintZone().getY();
      double maxTickLabelWidth = 0;
      for (int i = 0; i < axisTick.getTickLabels().size(); i++) {

        String tickLabel = axisTick.getTickLabels().get(i);
        // System.out.println(tickLabel);
        int tickLocation = axisTick.getTickLocations().get(i);

        if (tickLabel != null) { // some are null for logarithmic axes
          FontRenderContext frc = g.getFontRenderContext();
          // TextLayout layout = new TextLayout(tickLabel, font, new FontRenderContext(null, true, false));
          TextLayout layout = new TextLayout(tickLabel, getChartPainter().getStyleManager().getAxisTickLabelsFont(), frc);
          Rectangle2D tickLabelBounds = layout.getBounds();
          layout.draw(g, (float) xOffset, (float) (yOffset + axisTick.getAxis().getPaintZone().getHeight() - tickLocation + tickLabelBounds.getHeight() / 2.0));

          if (tickLabelBounds.getWidth() > maxTickLabelWidth) {
            maxTickLabelWidth = tickLabelBounds.getWidth();
          }
        }
      }

      // bounds
      bounds = new Rectangle2D.Double(xOffset, yOffset, maxTickLabelWidth, axisTick.getAxis().getPaintZone().getHeight());
      // g.setColor(Color.blue);
      // g.draw(bounds);

    }
    else if (axisTick.getAxis().getDirection() == Axis.Direction.X && getChartPainter().getStyleManager().isXAxisTicksVisible()) { // X-Axis

      double xOffset = axisTick.getAxis().getPaintZone().getX();
      double yOffset = axisTick.getAxis().getAxisTitle().getBounds().getY();
      double maxTickLabelHeight = 0;
      for (int i = 0; i < axisTick.getTickLabels().size(); i++) {

        String tickLabel = axisTick.getTickLabels().get(i);
        int tickLocation = axisTick.getTickLocations().get(i);

        if (tickLabel != null) { // some are null for logarithmic axes
          FontRenderContext frc = g.getFontRenderContext();
          TextLayout layout = new TextLayout(tickLabel, getChartPainter().getStyleManager().getAxisTickLabelsFont(), frc);
          Rectangle2D tickLabelBounds = layout.getBounds();
          layout.draw(g, (float) (xOffset + tickLocation - tickLabelBounds.getWidth() / 2.0), (float) yOffset);

          if (tickLabelBounds.getHeight() > maxTickLabelHeight) {
            maxTickLabelHeight = tickLabelBounds.getHeight();
          }
        }
      }

      // bounds
      bounds = new Rectangle2D.Double(xOffset, yOffset - maxTickLabelHeight, axisTick.getAxis().getPaintZone().getWidth(), maxTickLabelHeight);
      // g.setColor(Color.blue);
      // g.draw(bounds);

    }

  }

  @Override
  public ChartPainter getChartPainter() {

    return axisTick.getChartPainter();
  }
}
