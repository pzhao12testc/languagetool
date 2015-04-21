/* LanguageTool, a natural language style checker
 * Copyright (C) 2012 Ionuț Păduraru
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.synthesis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import morfologik.stemming.IStemmer;
import morfologik.stemming.WordData;

/**
 *  Adapter from {@link ManualSynthesizer} to {@link Synthesizer}. <br/> 
 *  Note: It resides in "test" package because for now it is only used on unit testing.
 */
public class ManualSynthesizerAdapter extends BaseSynthesizer {

  private final ManualSynthesizer manualSynthesizer;

  public ManualSynthesizerAdapter(ManualSynthesizer manualSynthesizer) {
    super(null, null); // no file
    this.manualSynthesizer = manualSynthesizer;
  }

  @Override
  protected IStemmer createStemmer() {
    return new IStemmer() { // null synthesiser 
      @Override
      public List<WordData> lookup(CharSequence word) {
        return Collections.emptyList();
      }
    };
  }
  
  @Override
  protected void initPossibleTags() throws IOException {
    if (possibleTags == null) {
      possibleTags = new ArrayList<>(manualSynthesizer.getPossibleTags());
    }
  }

  @Override
  protected void lookup(String lemma, String posTag, List<String> results) {
    super.lookup(lemma, posTag, results);
    final List<String> manualForms = manualSynthesizer.lookup(lemma.toLowerCase(), posTag);
    if (manualForms != null) {
      results.addAll(manualForms);
    }
  }

}
